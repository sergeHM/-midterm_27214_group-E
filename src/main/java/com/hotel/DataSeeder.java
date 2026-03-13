package com.hotel;

import com.hotel.entity.*;
import com.hotel.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ProvinceRepository    provinceRepo;
    private final LocationRepository    locationRepo;
    private final UserRepository        userRepo;
    private final UserProfileRepository profileRepo;
    private final RoleRepository        roleRepo;
    private final RoomRepository        roomRepo;
    private final BookingRepository     bookingRepo;
    private final FoodItemRepository    foodItemRepo;
    private final FoodOrderRepository   foodOrderRepo;
    private final PaymentRepository     paymentRepo;

    @Override
    public void run(String... args) {

        if (provinceRepo.count() > 0) {
            System.out.println("\n========================================");
            System.out.println("  Database already has data - skipping seed.");
            System.out.println("  API: http://localhost:8080/api");
            System.out.println("========================================\n");
            return;
        }

        System.out.println("\nSeeding initial data...");

        
        Province kigali = provinceRepo.save(Province.builder().provinceCode("KGL").provinceName("Kigali").build());
        Province east   = provinceRepo.save(Province.builder().provinceCode("E").provinceName("Eastern").build());
        Province west   = provinceRepo.save(Province.builder().provinceCode("W").provinceName("Western").build());
        Province north  = provinceRepo.save(Province.builder().provinceCode("N").provinceName("Northern").build());
        Province south  = provinceRepo.save(Province.builder().provinceCode("S").provinceName("Southern").build());

        
        Location nyarugenge = locationRepo.save(Location.builder().cityName("Nyarugenge").district("Nyarugenge").province(kigali).build());
        Location gasabo     = locationRepo.save(Location.builder().cityName("Gasabo").district("Gasabo").province(kigali).build());
        Location rwamagana  = locationRepo.save(Location.builder().cityName("Rwamagana").district("Rwamagana").province(east).build());
        Location rubavu     = locationRepo.save(Location.builder().cityName("Rubavu").district("Rubavu").province(west).build());
        Location musanze    = locationRepo.save(Location.builder().cityName("Musanze").district("Musanze").province(north).build());
        
        Location kicukiro = locationRepo.save(Location.builder().cityName("Kicukiro").district("Kicukiro").province(kigali).build());
        Location huye     = locationRepo.save(Location.builder().cityName("Huye").district("Huye").province(south).build());

        
        Role guestRole = roleRepo.save(Role.builder().roleName("GUEST").description("Hotel guest / customer").build());
        Role staffRole = roleRepo.save(Role.builder().roleName("STAFF").description("Hotel staff member").build());
        Role mgmtRole  = roleRepo.save(Role.builder().roleName("MANAGER").description("Hotel manager").build());

        
        User serge = userRepo.save(User.builder()
            .fullName("Habimana Mugisha Serge").email("serge@hotel.rw")
            .phoneNumber("0781234567").location(nyarugenge)
            .roles(Set.of(guestRole)).build());
        User alice = userRepo.save(User.builder()
            .fullName("Alice Uwase").email("alice@hotel.rw")
            .phoneNumber("0722345678").location(gasabo)
            .roles(Set.of(staffRole, mgmtRole)).build());
        User bob = userRepo.save(User.builder()
            .fullName("Bob Nkurunziza").email("bob@hotel.rw")
            .phoneNumber("0733456789").location(musanze)
            .roles(Set.of(guestRole)).build());
        
        User claire = userRepo.save(User.builder()
            .fullName("Claire Mutesi").email("claire@hotel.rw")
            .phoneNumber("0712987654").nationality("Rwandan").location(kicukiro)
            .roles(Set.of(guestRole)).build());
        User john = userRepo.save(User.builder()
            .fullName("John Smith").email("john@hotel.rw")
            .phoneNumber("0799000001").nationality("British").location(rubavu)
            .roles(Set.of(guestRole)).build());
        User marie = userRepo.save(User.builder()
            .fullName("Marie Mukamana").email("marie@hotel.rw")
            .phoneNumber("0788112233").nationality("Rwandan").location(gasabo)
            .roles(Set.of(staffRole)).build());

        
        profileRepo.save(UserProfile.builder().nationalId("1199080001234567").dateOfBirth("1990-05-12").gender("M").user(serge).build());
        profileRepo.save(UserProfile.builder().nationalId("1199280007654321").dateOfBirth("1992-08-22").gender("F").user(alice).build());
        profileRepo.save(UserProfile.builder().nationalId("1198880003456789").dateOfBirth("1988-11-03").gender("M").user(bob).build());
        
        profileRepo.save(UserProfile.builder().nationalId("1199480009871234").dateOfBirth("1994-02-17").gender("F").user(claire).build());
        profileRepo.save(UserProfile.builder().nationalId("9876543210000001").dateOfBirth("1985-07-30").gender("M").user(john).build());
        profileRepo.save(UserProfile.builder().nationalId("1199080005554321").dateOfBirth("1990-12-01").gender("F").user(marie).build());

        
        Room r101 = roomRepo.save(Room.builder().roomNumber("101").roomType(Room.RoomType.SINGLE).pricePerNight(50.0).features("Single bed, Wi-Fi, AC").isAvailable(false).build());
        Room r102 = roomRepo.save(Room.builder().roomNumber("102").roomType(Room.RoomType.SINGLE).pricePerNight(50.0).features("Single bed, Wi-Fi, AC").isAvailable(true).build());
        Room r201 = roomRepo.save(Room.builder().roomNumber("201").roomType(Room.RoomType.DOUBLE).pricePerNight(90.0).features("Double bed, Wi-Fi, AC, TV").isAvailable(true).build());
        Room r202 = roomRepo.save(Room.builder().roomNumber("202").roomType(Room.RoomType.DOUBLE).pricePerNight(90.0).features("Double bed, Wi-Fi, AC, TV").isAvailable(false).build());
        Room r301 = roomRepo.save(Room.builder().roomNumber("301").roomType(Room.RoomType.SUITE).pricePerNight(200.0).features("King bed, Jacuzzi, Wi-Fi, Minibar, City view").isAvailable(false).build());
        Room r401 = roomRepo.save(Room.builder().roomNumber("401").roomType(Room.RoomType.DELUXE).pricePerNight(350.0).features("King bed, Private pool, Butler service, Panoramic view").isAvailable(true).build());

        
        FoodItem breakfast = foodItemRepo.save(FoodItem.builder().itemName("Full English Breakfast").category(FoodItem.FoodCategory.BREAKFAST).price(12.0).description("Eggs, bacon, sausage, toast, beans").isAvailable(true).build());
        FoodItem coffee    = foodItemRepo.save(FoodItem.builder().itemName("Rwandan Coffee").category(FoodItem.FoodCategory.DRINK).price(3.0).description("Premium single-origin Rwandan coffee").isAvailable(true).build());
        FoodItem water     = foodItemRepo.save(FoodItem.builder().itemName("Mineral Water").category(FoodItem.FoodCategory.DRINK).price(2.0).description("500ml chilled mineral water").isAvailable(true).build());
        FoodItem chicken   = foodItemRepo.save(FoodItem.builder().itemName("Grilled Chicken").category(FoodItem.FoodCategory.LUNCH).price(18.0).description("Herb-marinated grilled chicken with fries").isAvailable(true).build());
        FoodItem steak     = foodItemRepo.save(FoodItem.builder().itemName("Beef Steak").category(FoodItem.FoodCategory.DINNER).price(28.0).description("250g sirloin with vegetables and sauce").isAvailable(true).build());
        FoodItem brochette = foodItemRepo.save(FoodItem.builder().itemName("Beef Brochette").category(FoodItem.FoodCategory.DINNER).price(15.0).description("Traditional Rwandan grilled beef skewers").isAvailable(true).build());
        FoodItem salad     = foodItemRepo.save(FoodItem.builder().itemName("Caesar Salad").category(FoodItem.FoodCategory.LUNCH).price(10.0).description("Romaine lettuce, croutons, parmesan").isAvailable(true).build());
        FoodItem cake      = foodItemRepo.save(FoodItem.builder().itemName("Chocolate Cake").category(FoodItem.FoodCategory.DESSERT).price(8.0).description("Rich chocolate layer cake").isAvailable(true).build());

        
        Booking booking1 = bookingRepo.save(Booking.builder()
                .user(serge).room(r101)
                .checkInDate(LocalDate.of(2025, 3, 1)).checkOutDate(LocalDate.of(2025, 3, 4))
                .totalNights(3).roomTotal(150.0).status(Booking.BookingStatus.CONFIRMED).build());
        Booking booking2 = bookingRepo.save(Booking.builder()
                .user(claire).room(r202)
                .checkInDate(LocalDate.of(2025, 3, 1)).checkOutDate(LocalDate.of(2025, 3, 3))
                .totalNights(2).roomTotal(180.0).status(Booking.BookingStatus.CONFIRMED).build());
        Booking booking3 = bookingRepo.save(Booking.builder()
                .user(bob).room(r301)
                .checkInDate(LocalDate.of(2025, 3, 2)).checkOutDate(LocalDate.of(2025, 3, 7))
                .totalNights(5).roomTotal(1000.0).status(Booking.BookingStatus.CONFIRMED).build());
        Booking booking4 = bookingRepo.save(Booking.builder()
                .user(john).room(r102)
                .checkInDate(LocalDate.of(2025, 2, 10)).checkOutDate(LocalDate.of(2025, 2, 12))
                .totalNights(2).roomTotal(100.0).status(Booking.BookingStatus.CHECKED_OUT).build());

        
        foodOrderRepo.save(FoodOrder.builder().booking(booking1).foodItem(breakfast).quantity(2).unitPrice(12.0).totalPrice(24.0).status(FoodOrder.OrderStatus.DELIVERED).build());
        foodOrderRepo.save(FoodOrder.builder().booking(booking1).foodItem(coffee).quantity(2).unitPrice(3.0).totalPrice(6.0).status(FoodOrder.OrderStatus.DELIVERED).build());
        foodOrderRepo.save(FoodOrder.builder().booking(booking1).foodItem(steak).quantity(1).unitPrice(28.0).totalPrice(28.0).status(FoodOrder.OrderStatus.PENDING).build());
        foodOrderRepo.save(FoodOrder.builder().booking(booking2).foodItem(chicken).quantity(1).unitPrice(18.0).totalPrice(18.0).status(FoodOrder.OrderStatus.DELIVERED).build());
        foodOrderRepo.save(FoodOrder.builder().booking(booking2).foodItem(water).quantity(3).unitPrice(2.0).totalPrice(6.0).status(FoodOrder.OrderStatus.DELIVERED).build());
        foodOrderRepo.save(FoodOrder.builder().booking(booking3).foodItem(brochette).quantity(2).unitPrice(15.0).totalPrice(30.0).status(FoodOrder.OrderStatus.DELIVERED).build());
        foodOrderRepo.save(FoodOrder.builder().booking(booking3).foodItem(cake).quantity(1).unitPrice(8.0).totalPrice(8.0).status(FoodOrder.OrderStatus.PENDING).build());

        
        paymentRepo.save(Payment.builder()
                .booking(booking4)
                .roomAmount(100.0).foodAmount(35.0).totalAmount(135.0)
                .paymentMethod(Payment.PaymentMethod.MOBILE_MONEY)
                .paymentStatus(Payment.PaymentStatus.PAID)
                .transactionRef("TXN-MOMO-00001").build());

        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║     GRAND HOTEL MANAGEMENT SYSTEM — READY!          ║");
        System.out.println("║     AUCA | Habimana Mugisha Serge                    ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║  Initial data seeded successfully.                  ║");
        System.out.println("║  API Base:   http://localhost:8080/api               ║");
        System.out.println("║                                                      ║");
        System.out.println("║  /api/provinces   /api/locations  /api/users         ║");
        System.out.println("║  /api/profiles    /api/rooms      /api/bookings      ║");
        System.out.println("║  /api/food-items  /api/food-orders  /api/payments    ║");
        System.out.println("╚══════════════════════════════════════════════════════╝\n");
    }
}
