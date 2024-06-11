# Instalar las librerías si no están instaladas
if (!require(shiny)) install.packages("shiny")
if (!require(plotly)) install.packages("plotly")
if (!require(jsonlite)) install.packages("jsonlite")
if (!require(httr)) install.packages("httr")
if (!require(ggplot2)) install.packages("ggplot2")
if (!require(dplyr)) install.packages("dplyr")

# Cargar las librerías
library(shiny)
library(plotly)
library(jsonlite)
library(httr)
library(ggplot2)
library(dplyr)

# Función para calcular el tiempo restante hasta el próximo intervalo de 10 minutos
calculateTimeLeft <- function() {
  current_time <- as.POSIXlt(Sys.time())
  minutes_left <- (10 - current_time$min %% 10) %% 10 # minutos restantes hasta el próximo intervalo de 10 minutos
  seconds_left <- (60 - current_time$sec) %% 60 # segundos restantes hasta el próximo minuto
  time_left <- minutes_left * 60 + seconds_left # tiempo total restante en segundos
  return(time_left)
}

# Temporizador reactivo para actualizar cada 10 minutos en los minutos 00, 10, 20, 30, 40, 50
autoUpdate <- reactiveTimer({
  calculateTimeLeft()
})

# Define UI for application
ui <- fluidPage(
  
  # Application title
  titlePanel("Metal Dashboard"),
  
  # Sidebar layout
  sidebarLayout(
    sidebarPanel(
      selectInput("metal_select",
                  "Select Metal:",
                  choices = c("Copper", "Gold", "Palladium", "Platinum", "Silver"),
                  selected = "Copper"),
      
      # Botonera
      fluidRow(
        column(12,
               actionButton("H1_button", "H1"),
               actionButton("H4_button", "H4"),
               actionButton("D_button", "D"),
               actionButton("S_button", "S")
        )
      )
    ),
    
    # Main panel
    mainPanel(
      plotlyOutput("distPlot")
    )
  )
)

# Define server logic required to draw the plot
server <- function(input, output, session) {
  
  # Reactive value to store the selected interval and currency
  values <- reactiveValues(interval = "h1")
  
  # Function to fetch data from API
  fetchData <- function(metal, interval) {
    # Fetch historical data if interval is D or S
    if (interval %in% c("D", "S")) {
      url_hist <- paste0("http://localhost:8090/mped/metalHD/", tolower(metal), "/",interval)
      response_hist <- GET(url_hist)
      if (!http_error(response_hist)) {
        data_hist <- fromJSON(content(response_hist, "text", encoding = "UTF-8"))
        df_hist <- data.frame(
          datetime = as.POSIXct(data_hist$datetime, 
                                format = ifelse(interval %in% c("D", "S"), "%d.%m.%Y", "%Y-%m-%dT%H:%M:%OSZ"), 
                                tz = "UTC"),
          openPrice = data_hist$openPrice,
          closePrice = data_hist$closePrice,
          highPrice = data_hist$highPrice,
          lowPrice = data_hist$lowPrice
        )
      } else {
        showNotification("Error al obtener los datos históricos", type = "error")
        return(NULL)
      }
    } else {
      df_hist <- NULL
    }
    
    # Fetch current data
    url <- paste0("http://localhost:8090/mped/", tolower(metal), interval, "/USD")
    response <- GET(url)
    if (http_error(response)) {
      showNotification("Error al obtener los datos", type = "error")
      return(NULL)
    } else {
      data <- fromJSON(content(response, "text", encoding = "UTF-8"))
      df <- data.frame(
        datetime = as.POSIXct(data$datetime, format="%Y-%m-%dT%H:%M:%OSZ", tz="UTC"),
        openPrice = data$openPrice,
        closePrice = data$closePrice,
        highPrice = data$highPrice,
        lowPrice = data$lowPrice
      )
    }
    
    if (interval %in% c("D", "S")){
      # Eliminating duplicates
      df_hist <- distinct(df_hist, datetime, .keep_all = TRUE)
    }
    
    
    # Combine historical and current data
    if (!is.null(df_hist)) {
      df <- rbind(df_hist, df)  
    }
    
    df_seg <- df[, c("datetime", "openPrice", "closePrice", "highPrice", "lowPrice")]
    return(df_seg)
  }
  
  
  # Function to update the plot
  updatePlot <- reactive({
    autoUpdate() # Esto activará el temporizador y actualizará cada 10 minutos en los minutos 00, 10, 20, 30, 40, 50
    
    metal <- input$metal_select
    interval <- values$interval
    df_seg <- fetchData(metal, interval)
    req(df_seg)
    p <- plot_ly(data = df_seg, x = ~datetime, type = 'candlestick',
                 open = ~openPrice, close = ~closePrice,
                 high = ~highPrice, low = ~lowPrice) %>%
      layout(title = paste(metal, "Price (", toupper(interval), ")", sep = " "),
             xaxis = list(rangeslider = list(visible = FALSE)))
    p <- plotly::config(p, scrollZoom = TRUE)
    p
    
  })
  
  # Observers to update the interval based on button clicks
  observeEvent(input$H1_button, {
    values$interval <- "h1"
  })
  
  observeEvent(input$H4_button, {
    values$interval <- "h4"
  })
  
  observeEvent(input$D_button, {
    values$interval <- "D"
  })
  
  observeEvent(input$S_button, {
    values$interval <- "S"
  })
  
  # Render the plot
  output$distPlot <- renderPlotly({
    updatePlot()
  })
  
}

# Run the application 
shinyApp(ui = ui, server = server)
