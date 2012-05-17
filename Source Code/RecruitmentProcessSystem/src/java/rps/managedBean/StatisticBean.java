/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import rps.business.StatisticService;
import rps.entities.statistic.ApplicantStatistic;
import rps.entities.statistic.VacancyStatistic;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class StatisticBean {

    private StatisticService statisticService;

    /** Creates a new instance of StatisticBean */
    public StatisticBean() {
        statisticService = new StatisticService();
        getFavoriteVacancy();
        getNumberRegisteredApplicant();
    }
    private CartesianChartModel vacancyModel;

    public CartesianChartModel getVacancyModel() {
        return vacancyModel;
    }

    private void getFavoriteVacancy() {
        try {
            List<VacancyStatistic> list = new ArrayList<VacancyStatistic>();
            list = statisticService.getFavoriteVacancy(5);
            vacancyModel = new CartesianChartModel();

            ChartSeries vacancies = new ChartSeries();
            vacancies.setLabel("Number of registered applicants");

            for (VacancyStatistic vs : list) {
                vacancies.set(vs.getVacancy().getTitle(), vs.getNumberApplicants());
            }

            vacancyModel.addSeries(vacancies);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private CartesianChartModel applicantModel;

    public CartesianChartModel getApplicantModel() {
        return applicantModel;
    }

    private void getNumberRegisteredApplicant() {
        try {
            List<ApplicantStatistic> list = new ArrayList<ApplicantStatistic>();
            list = statisticService.findNumberRegisteredApplicant();
            applicantModel = new CartesianChartModel();

            ChartSeries applicants = new ChartSeries();
            applicants.setLabel("Number of registered applicants");

            for (ApplicantStatistic as : list) {
                if (as.getAmount() == -1) {
                    applicants.set(as.getMonth(), null);
                } else {
                    applicants.set(as.getMonth(), as.getAmount());
                }
            }

            applicantModel.addSeries(applicants);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
