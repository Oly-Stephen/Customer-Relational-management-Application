package com.example.application.views;

import com.example.application.service.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard | CRM")
@PermitAll
public class DashBoardView extends VerticalLayout {
    private final CrmService service;

    public DashBoardView(CrmService service) {
        this.service = service;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(getContactStats(), getCompaniesChart());
    }

    private Component getContactStats() {
        Span stats = new Span(service.countContacts() + "contact");
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

    private Component getCompaniesChart() {
        Chart chart = new Chart(ChartType.PIE);

        DataSeries dataSeries = new DataSeries();
        service.findAllCompanies().forEach((company -> {
            dataSeries.add(new DataSeriesItem(company.getName(), company.getEmployeeCount()));
        }));
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }
}
