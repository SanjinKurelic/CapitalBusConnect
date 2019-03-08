/*
 * Created by Sanjin Kurelic (kurelic@sanjin.eu)
 */

package eu.sanjin.kurelic.cbc.business.viewmodel.schedule;

import java.time.LocalDateTime;

public interface ScheduleItem {

    int getScheduleId();
    
    String getLeftTitle();
    
    String getRightTitle();
    
    String getDescription();
    
    int getNumberOfAdults();
    
    int getNumberOfChildren();
    
    double getPrice();
    
    ScheduleButtonType getButton();
    
    ScheduleItemType getType();

    LocalDateTime getDate();

    ScheduleUpdateType getOnUpdate();

    SchedulePayingMethod getPayingMethod();

    boolean disabled();
    
}
