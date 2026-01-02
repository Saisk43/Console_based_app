package ConcertBookingSys;

import java.time.LocalDateTime;

public class main {

    public static void main(String[] args) {
        User user = new User("sse", "sar", "null");
        Concert con = new Concert("null", "ar", "ve", LocalDateTime.now());
    }
}