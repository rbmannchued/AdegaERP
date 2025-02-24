package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import org.example.services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class RelatoriosController implements Initializable {
     @Autowired
     private VendaService vendaService;


     @FXML
     private BarChart<String, Number> graficoVendas;

    public void carregarGraficoVendasAno(int ano) {
        // Busca os dados de vendas agrupados por mês
        List<Object[]> vendasPorMes = vendaService.contarVendasPorMes(ano);

        // Cria a série de dados para o gráfico
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Vendas no Ano " + ano);

        // Adiciona os dados ao gráfico
        for (Object[] row : vendasPorMes) {
            Integer mes = (Integer) row[0];  // Mês (1 = Janeiro, 2 = Fevereiro, ...)
            Long totalVendas = (Long) row[1]; // Quantidade de vendas no mês

            String nomeMes = obterNomeMes(mes);  // Converte o número do mês para nome
            series.getData().add(new XYChart.Data<>(nomeMes, totalVendas));
        }

        // Limpa os dados antigos e adiciona os novos
        graficoVendas.getData().clear();
        graficoVendas.getData().add(series);
    }

    // Método para converter número do mês para nome
    private String obterNomeMes(int mes) {
        String[] meses = {
                "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
        };
        return meses[mes - 1]; // Como o índice começa em 0, subtrai 1
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int anoAtual = LocalDate.now().getYear();
        carregarGraficoVendasAno(anoAtual);
    }
}
