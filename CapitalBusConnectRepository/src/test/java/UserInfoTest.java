

public class UserInfoTest {
/*
    @Test
    public void getUserInfo() {
        var session = RepositoryConfiguration.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        User c = session.get(User.class, "sanjin");
        System.out.println(c.getName() + " with password " + c.getPassword());
        session.getTransaction().commit();
    }

    @Test
    public void getUserLoginHistory() {
        var session = RepositoryConfiguration.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        UserLoginHistory h = session.get(UserLoginHistory.class, "sanjin");
        System.out.println(h.getIpAddress() + " -> " + h.getDateTime());
        session.getTransaction().commit();
    }

    @Test
    public void getUserHistory() {
        var session = RepositoryConfiguration.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        UserTravelHistory h = session.get(UserTravelHistory.class, new UserPrimaryKey(1, "sanjin"));
        System.out.println("Adults: " + h.getNumberOfAdults() + ", Children: " + h.getNumberOfChildren());
        System.out.println("Date: " + h.getTripHistory().getDate() + ", Paying method: " + h.getPayingMethod().getName());
        session.getTransaction().commit();
    }

    /*@Test
    public void getAuthorities() {
        var session = RepositoryConfiguration.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Authorities a = session.get(Authorities.class, "sanjin");
        System.out.println(a.getUsername() + " with authorities " + a.getAuthority());
        session.getTransaction().commit();
    }*/

}
