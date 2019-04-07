package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItems;
import eu.sanjin.kurelic.cbc.repo.dao.*;
import eu.sanjin.kurelic.cbc.repo.entity.BusSchedule;
import eu.sanjin.kurelic.cbc.repo.entity.TripHistory;
import eu.sanjin.kurelic.cbc.repo.entity.UserTravelHistory;
import eu.sanjin.kurelic.cbc.repo.values.PayingMethodValues;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.temporal.ChronoUnit;

@Service
@Scope("singleton")
public class CartServiceImpl implements CartService {

    private CartItems items;
    private final BusScheduleDao busScheduleDao;
    private final TripHistoryDao tripHistoryDao;
    private final TripTypeDao tripTypeDao;
    private final TripPricesDao tripPricesDao;
    private final UserTravelHistoryDao userTravelHistoryDao;
    private final PayingMethodDao payingMethodDao;

    public CartServiceImpl(@Qualifier("busScheduleDaoImpl") BusScheduleDao busScheduleDao, @Qualifier("tripHistoryDaoImpl") TripHistoryDao tripHistoryDao, @Qualifier("tripTypeDaoImpl") TripTypeDao tripTypeDao, @Qualifier("tripPricesDaoImpl") TripPricesDao tripPricesDao, @Qualifier("userTravelHistoryDaoImpl") UserTravelHistoryDao userTravelHistoryDao, @Qualifier("payingMethodDaoImpl") PayingMethodDao payingMethodDao) {
        this.busScheduleDao = busScheduleDao;
        this.tripHistoryDao = tripHistoryDao;
        this.tripTypeDao = tripTypeDao;
        this.tripPricesDao = tripPricesDao;
        this.userTravelHistoryDao = userTravelHistoryDao;
        this.payingMethodDao = payingMethodDao;
    }

    @Override
    public void loadCartItems(CartItems items) {
        this.items = items;
    }

    @Override
    public boolean hasCartItem(CartItem cartItem) {
        return items.contains(cartItem);
    }

    @Override
    public boolean addCartItem(CartItem cartItem) {
        if (!hasCartItem(cartItem)) {
            return items.add(cartItem);
        }
        return false;
    }

    @Override
    public boolean updateCartItem(CartItem cartItem) {
        try {
            items.set(items.indexOf(cartItem), cartItem);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean removeCartItem(CartItem cartItem) {
        return items.remove(cartItem);
    }

    @Override
    public boolean removeAllCartItems() {
        items.clear();
        return true;
    }

    @Override
    @Transactional
    public boolean saveToDatabase(PayingMethodValues payingMethod, String username) {
        UserTravelHistory travelHistory;
        BusSchedule scheduleItem;
        int duration;

        for (CartItem item : items) {
            travelHistory = new UserTravelHistory();
            scheduleItem = busScheduleDao.getSchedule(item.getScheduleId());

            travelHistory.setUsername(username);
            travelHistory.setNumberOfAdults(item.getNumberOfAdults());
            travelHistory.setNumberOfChildren(item.getNumberOfChildren());
            travelHistory.setPayingMethod(payingMethodDao.getPayingMethodByName(payingMethod));
            //TODO remove ABS function
            duration = (int) Math.abs(Math.ceil(ChronoUnit.MINUTES.between(scheduleItem.getFromTime(), scheduleItem.getToTime()) / 60.0));
            travelHistory.setPrice(tripPricesDao.getTripPrice(duration).getPrice());
            travelHistory.setTripHistory(getTripHistory(item, scheduleItem));
            // Store user travel history
            if (!userTravelHistoryDao.addUserTravelHistory(travelHistory)) {
                return false;
            }
        }
        return true;
    }

    private TripHistory getTripHistory(CartItem item, BusSchedule scheduleItem) {
        TripHistory tripHistory;
        int id = tripHistoryDao.hasTripHistory(scheduleItem, item.getDate().toLocalDate(), tripTypeDao.getTripType(item.getTripType()));
        int numberOfSeats = 0;
        if (id != 0) {
            tripHistory = tripHistoryDao.getTripHistory(id);
            numberOfSeats = tripHistory.getNumberOfSeats();
        } else {
            tripHistory = new TripHistory();
            tripHistory.setDate(item.getDate().toLocalDate());
            tripHistory.setTripType(tripTypeDao.getTripType(item.getTripType()));
            tripHistory.setBusSchedule(scheduleItem);
            numberOfSeats = scheduleItem.getBusType().getNumberOfSeats();
        }
        tripHistory.setNumberOfSeats(numberOfSeats  - (item.getNumberOfAdults() + item.getNumberOfChildren()));
        tripHistoryDao.addOrUpdateTripHistory(tripHistory);
        return tripHistory;
    }

    @Override
    public CartItems getCartItems() {
        return items;
    }
}
