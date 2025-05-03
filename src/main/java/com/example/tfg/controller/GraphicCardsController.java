package com.example.tfg.controller;

import com.example.tfg.model.Player;
import com.example.tfg.model.Team;
import com.example.tfg.model.User;
import com.example.tfg.service.PlayerServiceImpl;
import javafx.application.Platform;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Comparator;
import java.util.List;

@Controller
public class GraphicCardsController {
    private static final Logger logger = LoggerFactory.getLogger(GraphicCardsController.class);

    @Autowired
    private PlayerServiceImpl playerServiceImpl;

    public void setupCardsChart(Scene cardsScene, User loggedInUser) {
        try {
            StackedBarChart<String, Number> cardsChart =
                    (StackedBarChart<String, Number>) cardsScene.lookup("#graphic_cards");


            if (cardsChart != null) {
                // Limpiar datos previos
                cardsChart.getData().clear();

                // Configurar datos del gráfico
                configureCardsChart(cardsChart, loggedInUser);
            } else {
                logger.error("No se encontró el gráfico con ID 'graphic_cards'");
            }
        } catch (Exception e) {
            logger.error("Error al configurar el gráfico de tarjetas", e);
        }
    }

    private void configureCardsChart(StackedBarChart<String, Number> cardsChart, User loggedInUser) {
        try {
            // Obtener los jugadores según el equipo del usuario logueado
            List<Player> players;
            if (loggedInUser != null && loggedInUser.getTeam() != null) {
                Team team = loggedInUser.getTeam();
                players = playerServiceImpl.findByTeamName(team);
                logger.info("Mostrando tarjetas para el equipo: {}", team.getName());
            } else {
                players = playerServiceImpl.findAll();
                logger.info("Mostrando tarjetas para todos los jugadores");
            }

            if (players.isEmpty()) {
                logger.warn("No hay jugadores para mostrar en el gráfico");
                return;
            }

            // Ordenar jugadores por cantidad total de tarjetas (amarillas + rojas) de mayor a menor
            players.sort(Comparator.comparingInt(player ->
                    -(player.getYellowCards() + player.getRedCards())));


            // Crear series para tarjetas amarillas y rojas
            XYChart.Series<String, Number> yellowSeries = new XYChart.Series<>();
            yellowSeries.setName("Yellow Cards");

            XYChart.Series<String, Number> redSeries = new XYChart.Series<>();
            redSeries.setName("Red Cards");

            // Agregar datos de cada jugador
            for (Player player : players) {
                // Formato del nombre: "Apellido, D#" (donde # es el dorsal)
                String displayName = String.format("%s, %d",
                        player.getApodo(),
                        player.getDorsal());

                // Agregar datos a cada serie
                yellowSeries.getData().add(new XYChart.Data<>(displayName, player.getYellowCards()));
                redSeries.getData().add(new XYChart.Data<>(displayName, player.getRedCards()));
            }

            // Agregar series al gráfico
            cardsChart.getData().addAll(yellowSeries, redSeries);

            // Configuración del gráfico
            configureChartLayout(cardsChart);

            // Aplicar estilos a las barras
            styleChartBars(cardsChart);

        } catch (Exception e) {
            logger.error("Error al configurar datos del gráfico", e);
        }
    }

    private void configureChartLayout(StackedBarChart<String, Number> cardsChart) {
        // Configuración del eje X color de fuente negro
        CategoryAxis xAxis = (CategoryAxis) cardsChart.getXAxis();
        xAxis.setLabel("Players");
        xAxis.setTickLabelRotation(25);
        xAxis.setTickLabelFont(new Font(16));
        xAxis.setTickLabelFill(Color.BLACK);
        xAxis.setTickLabelGap(5);



        // Configuración del eje Y para mostrar solo números enteros sin duplicados
        NumberAxis yAxis = (NumberAxis) cardsChart.getYAxis();
        yAxis.setLabel("Cards");
        yAxis.setAutoRanging(false); // Desactivar autoajuste
        yAxis.setLowerBound(0); // Empezar desde 0
        yAxis.setTickLabelFill(Color.BLACK);

// Calcular el valor máximo para el límite superior
        Platform.runLater(() -> {
            // Encontrar el valor máximo del dataset
            double maxValue = 0;
            for (XYChart.Series<String, Number> series : cardsChart.getData()) {
                for (XYChart.Data<String, Number> data : series.getData()) {
                    maxValue = Math.max(maxValue, data.getYValue().doubleValue());
                }
            }

            // Redondear hacia arriba para asegurar espacio suficiente
            int upperBound = (int) Math.ceil(maxValue);
            if (upperBound == maxValue) upperBound++; // Añadir uno más si es exacto

            yAxis.setUpperBound(upperBound);
            yAxis.setTickUnit(1.0); // Exactamente 1 unidad entre marcas

            // Forzar marcas solo en valores enteros
            yAxis.setMinorTickCount(0);
            yAxis.setMinorTickVisible(false);
        });

// Formatear números como enteros
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
            @Override
            public String toString(Number object) {
                return String.format("%d", object.intValue());
            }
        });



        // Establecer valores fijos más moderados
        cardsChart.setPrefWidth(1200);
        cardsChart.setPrefHeight(700);

        // Ajustar espaciado entre categorías (jugadores)
        cardsChart.setCategoryGap(10); // Reducido para acomodar más jugadores
        cardsChart.setStyle("-fx-bar-gap: 3; -fx-category-gap: 10;");

        // Configurar leyenda
        cardsChart.setLegendVisible(false);
        cardsChart.setLegendSide(Side.RIGHT);

        // Título
        cardsChart.setTitle("Cards per Player");
        cardsChart.setTitleSide(Side.TOP);
        cardsChart.setAnimated(false);

        // Aplicar estilos al título y ejes
        Platform.runLater(() -> {
            // Aplicar estilos a títulos y ejes
            Node title = cardsChart.lookup(".chart-title");
            if (title instanceof Label) {
                ((Label) title).setStyle("-fx-font-weight: bold; -fx-font-size: 22px;");
                ((Label) title).setTextFill(Color.BLACK);
            }

            Node xAxisLabel = xAxis.lookup(".axis-label");
            if (xAxisLabel instanceof Label) {
                ((Label) xAxisLabel).setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
                ((Label) xAxisLabel).setTextFill(Color.BLACK);
            }

            Node yAxisLabel = yAxis.lookup(".axis-label");
            if (yAxisLabel instanceof Label) {
                ((Label) yAxisLabel).setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
                ((Label) yAxisLabel).setTextFill(Color.BLACK);
            }

            // Forzar actualización del gráfico
            cardsChart.requestLayout();
        });
    }

    private void styleChartBars(StackedBarChart<String, Number> chart) {
        // Aplicar estilos después del renderizado
        Platform.runLater(() -> {
            try {
                // Dar tiempo para que los nodos se creen
                Thread.sleep(100);

                if (chart.getData().size() >= 2) {
                    // Aplicar estilo amarillo a la primera serie
                    for (XYChart.Data<String, Number> data : chart.getData().get(0).getData()) {
                        Node node = data.getNode();
                        if (node != null) {
                            node.setStyle("-fx-bar-fill: gold;");
                        }
                    }

                    // Aplicar estilo rojo a la segunda serie
                    for (XYChart.Data<String, Number> data : chart.getData().get(1).getData()) {
                        Node node = data.getNode();
                        if (node != null) {
                            node.setStyle("-fx-bar-fill: red;");
                        }
                    }

                    // Forzar actualización
                    chart.requestLayout();
                }
            } catch (Exception e) {
                logger.error("Error al aplicar estilos a las barras", e);
            }
        });
    }
}