/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.business;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import rps.dataaccess.InterviewDA;
import rps.entities.statistic.ApplicantStatistic;
import rps.entities.statistic.VacancyStatistic;

/**
 *
 * @author user
 */
public class StatisticService extends AbstractService {

    private InterviewDA interviewDA;

    public StatisticService() {
        interviewDA = new InterviewDA(getEntityManager());
    }

    public List<VacancyStatistic> getFavoriteVacancy(int num) {
        return interviewDA.findFavoriteVacancy(num);
    }

    public List<ApplicantStatistic> findNumberRegisteredApplicant() {
        List<ApplicantStatistic> result = new ArrayList<ApplicantStatistic>();
        Calendar calendar = Calendar.getInstance();
        Date d = new Date();
        calendar.setTime(d);
        int currentMonth = d.getMonth();
        int months = calendar.getActualMaximum(Calendar.MONTH);

        for (int i = 0; i < months; i++) {
            Date startDate = new Date(d.getYear(), i, 1);
            calendar.setTime(startDate);
            int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            Date endDate = new Date(d.getYear(), i, days);
            Format formatter = new SimpleDateFormat("MMMM");
            String month = formatter.format(startDate);

            ApplicantStatistic as = new ApplicantStatistic();
            as.setMonth(month);
            if (i > currentMonth) {
                as.setAmount(-1);
            } else {
                as.setAmount(interviewDA.findNumberRegisteredApplicant(startDate, endDate));
            }
            result.add(as);
        }
        return result;
    }
}
