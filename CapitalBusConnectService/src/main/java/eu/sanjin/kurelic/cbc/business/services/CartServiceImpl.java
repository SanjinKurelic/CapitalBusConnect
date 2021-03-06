package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.exception.InvalidCartItemException;
import eu.sanjin.kurelic.cbc.business.exception.InvalidSuppliedArgumentsException;
import eu.sanjin.kurelic.cbc.business.exception.InvalidUserException;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItems;
import eu.sanjin.kurelic.cbc.repo.dao.*;
import eu.sanjin.kurelic.cbc.repo.entity.BusSchedule;
import eu.sanjin.kurelic.cbc.repo.entity.TripHistory;
import eu.sanjin.kurelic.cbc.repo.entity.UserTravelHistory;
import eu.sanjin.kurelic.cbc.repo.values.PayingMethodValues;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Objects;
import java.util.Set;

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
    private final UserService userService;

    public CartServiceImpl(BusScheduleDao busScheduleDao, TripHistoryDao tripHistoryDao, TripTypeDao tripTypeDao,
                           TripPricesDao tripPricesDao, UserTravelHistoryDao userTravelHistoryDao,
                           PayingMethodDao payingMethodDao, UserService userService) {
        this.busScheduleDao = busScheduleDao;
        this.tripHistoryDao = tripHistoryDao;
        this.tripTypeDao = tripTypeDao;
        this.tripPricesDao = tripPricesDao;
        this.userTravelHistoryDao = userTravelHistoryDao;
        this.payingMethodDao = payingMethodDao;
        this.userService = userService;
    }

    @Override
    public void loadCartItems(CartItems items) {
        this.items = items;
    }

    @Override
    public boolean hasCartItem(CartItem cartItem) {
        if (Objects.isNull(cartItem)) {
            return false;
        }
        return items.contains(cartItem);
    }

    @Override
    public void addCartItem(CartItem cartItem) throws InvalidCartItemException {
        // Check
        checkCartItem(cartItem);
        if (hasCartItem(cartItem)) {
            throw new InvalidCartItemException();
        }
        // Logic
        items.add(cartItem);
    }

    @Override
    public void updateCartItem(CartItem cartItem) throws InvalidCartItemException {
        // Check
        checkCartItem(cartItem);
        if (!hasCartItem(cartItem)) {
            throw new InvalidCartItemException();
        }
        // Logic
        items.set(items.indexOf(cartItem), cartItem);
    }

    @Override
    public void removeCartItem(CartItem cartItem) throws InvalidCartItemException {
        // Check
        checkCartItem(cartItem);
        if (!hasCartItem(cartItem)) {
            throw new InvalidCartItemException();
        }
        // Logic
        items.remove(cartItem);
    }

    @Override
    public void removeAllCartItems() {
        items.clear();
    }

    @Override
    @Transactional
    public void saveToDatabase(PayingMethodValues payingMethod, String username) throws InvalidSuppliedArgumentsException, InvalidUserException {
        UserTravelHistory travelHistory;
        BusSchedule scheduleItem;
        // Check
        if (Objects.isNull(payingMethod) || Objects.isNull(username)) {
            throw new InvalidSuppliedArgumentsException();
        }
        // Check if user is somehow invalid
        if (!userService.hasUser(username)) {
            throw new InvalidUserException();
        }
        for (CartItem item : items) {
            travelHistory = new UserTravelHistory();
            scheduleItem = busScheduleDao.getSchedule(item.getScheduleId());
            // Fill info
            travelHistory.setUsername(username);
            travelHistory.setNumberOfAdults(item.getNumberOfAdults());
            travelHistory.setNumberOfChildren(item.getNumberOfChildren());
            travelHistory.setPayingMethod(payingMethodDao.getPayingMethodByName(payingMethod));
            travelHistory.setPrice(tripPricesDao.getTripPrice(scheduleItem.getDuration()).getPrice());
            travelHistory.setTripHistory(getTripHistory(item, scheduleItem));
            // Store user travel history
            userTravelHistoryDao.addUserTravelHistory(travelHistory);
        }
    }

    @Override
    public CartItems getCartItems() {
        return items;
    }

    // Utility
    private TripHistory getTripHistory(CartItem item, BusSchedule scheduleItem) {
        TripHistory tripHistory;
        Integer id = tripHistoryDao.getTripHistoryIdOrNull(scheduleItem, item.getDate().toLocalDate(),
                tripTypeDao.getTripType(item.getTripType()));
        int numberOfSeats;
        if (!Objects.isNull(id)) {
            tripHistory = tripHistoryDao.getTripHistory(id);
            numberOfSeats = tripHistory.getNumberOfSeats();
        } else {
            tripHistory = new TripHistory();
            tripHistory.setDate(item.getDate().toLocalDate());
            tripHistory.setTripType(tripTypeDao.getTripType(item.getTripType()));
            tripHistory.setBusSchedule(scheduleItem);
            numberOfSeats = scheduleItem.getBusType().getNumberOfSeats();
        }
        tripHistory.setNumberOfSeats(numberOfSeats - (item.getNumberOfAdults() + item.getNumberOfChildren()));
        tripHistoryDao.addOrUpdateTripHistory(tripHistory);
        return tripHistory;
    }

    private void checkCartItem(CartItem item) throws InvalidCartItemException {
        if (Objects.isNull(item)) {
            throw new InvalidCartItemException();
        }
        var validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<CartItem>> violations = validator.validate(item);

        if (!violations.isEmpty()) {
            throw new InvalidCartItemException(violations);
        }
    }

}
