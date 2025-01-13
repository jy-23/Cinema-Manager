package cinema;

import com.mysql.cj.conf.ConnectionUrlParser;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DbConnector {
    private final String host = "jdbc:mysql://127.0.0.1:3306/cinema_schema";
    private final String username = "root";
    private final String password = "Rice2Cake!"; // enter password
    Connection connection = null;
    //DbComm communicator = new DbComm();

    public void createConnection() {
        try {
            connection = DriverManager.getConnection(host, username, password);
            System.out.println("Successfully connected to mySQL database");
            //communicator.setConnection(connection);

        }
        catch(SQLException e) {
            System.out.println("Failed to connect to mySQL database.");
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
            System.out.println("Closed connection to mySQL database");
        }
        catch(SQLException e) {
            System.out.println("Failed to close mySQL database");
            e.printStackTrace();
        }
    }

    // get dates as string in an Arraylist
    public ArrayList<Date> getDates() {
        ArrayList<Date> dates = new ArrayList<>();//{"one", "two"};
        String query = "select \n" +
                "\trow_number() over(order by date) as rowNum,\n" +
                "date\n" +
                "from showtimes\n" +
                "group by date;";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                int rowNum = rs.getInt(1);
                Date date = rs.getDate(2);
                System.out.printf("%3s %25s%n",
                        rowNum,
                        new SimpleDateFormat("E, MMM d, yyyy").format(date));
                dates.add(date);
            }
        }
        catch(SQLException e) {
            System.out.println("Failed to create statement");
            e.printStackTrace();
        }
        return dates;
    }

    public ArrayList<String> getMovies() {
        ArrayList<String> movieList = new ArrayList<String>();
        String query = "select \n" +
                "\trow_number() over(order by releasedate desc) as rowNum,\n" +
                "title,\n" +
                "director,\n" +
                "releasedate\n" +
                "from movies;";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            System.out.println("----------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Movies Showing Now");
            System.out.printf("%3s %50s %30s %15s%n",
                    " ",
                    "MOVIE TITLE",
                    "DIRECTOR",
                    "RELEASE DATE"
            );
            while (rs.next()) {
                int rowNum = rs.getInt(1);
                String movieTitle = rs.getString(2);
                movieList.add(movieTitle);
                String director = rs.getString(3);
                Date releaseDate = rs.getDate(4);

                System.out.printf("%3s %50s %30s %15s%n",
                        rowNum,
                        movieTitle,
                        director,
                        releaseDate);
            }
            System.out.println("----------------------------------------------------------------------------------------------------------------------------");

        }
        catch(SQLException e) {
            System.out.println("Failed to create statement");
            e.printStackTrace();
        }
        return movieList;
    }

    public ArrayList<Integer> getMoviesByDate(Date date) {
        ArrayList<Integer> moviesList = new ArrayList<>();
        String query = "select\n" +
                "\trow_number() over(order by date, time, title),\n" +
                "showtimes.time,\n" +
                "movies.title,\n" +
                "showtimes.showtimeid\n" +
                "from showtimes\n" +
                "left join movies on showtimes.movieid = movies.movieid\n" +
                "where date = '" + date + "'\n;";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            System.out.println("----------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("Movie Showings for %s%n", date);
            System.out.printf("%3s %10s %50s%n",
                    " ",
                    "SHOWTIME",
                    "MOVIE TITLE"
            );
            while (rs.next()) {
                int rowNum = rs.getInt(1);
                Time showTime = rs.getTime(2);
                String movieTitle = rs.getString(3);
                int showTimeId = rs.getInt(4);
                moviesList.add(showTimeId);

                System.out.printf("%3s %10s %50s%n",
                        rowNum,
                        new SimpleDateFormat("h:mm a").format(showTime),
                        movieTitle
                );
            }
            System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        }
        catch(SQLException e) {
            System.out.println("Failed to create statement");
            e.printStackTrace();
        }
        return moviesList;
    }

    public ArrayList<Integer> getMovieShowings(String movieTitle) {
        ArrayList<Integer> showTimeList = new ArrayList<>();
        String query = "SELECT \n" +
                "\trow_number() over(order by showtimes.date, showtimes.time) as rowNum,\n" +
                "showtimes.date,\n" +
                "showtimes.time,\n" +
                "showtimes.showtimeid\n" +
                "FROM showtimes\n" +
                "left join movies on showtimes.movieid = movies.movieid\n" +
                "where movies.title = \"" + movieTitle + "\";\n";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            System.out.printf("Showtimes for %s%n", movieTitle);

            while (rs.next()) {
                int rowNum = rs.getInt(1);
                Date date = rs.getDate(2);
                Time time = rs.getTime(3);
                int showtimeId = rs.getInt(4);
                System.out.printf("%5d %25s %10s%n",
                        rowNum,
                        new SimpleDateFormat("E, MMM d, yyyy").format(date),
                        new SimpleDateFormat("h:mm a").format(time)
                );

                showTimeList.add(showtimeId);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return showTimeList;
    }

    public Theater getTheater(int showtimeId) {
        Theater theater = new Theater();
        String query = "select\n" +
                "\tnumrow,\n" +
                "    numcol,\n" +
                "    chart\n" +
                "from showtimes\n" +
                "left join theaters on showtimes.theaterid = theaters.theaterid\n" +
                "left join seating_chart on showtimes.showtimeid = seating_chart.showtimeid\n" +
                "where showtimes.showtimeid = " + showtimeId + ";";

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()) {
                int numRow = rs.getInt(1);
                int numCol = rs.getInt(2);
                char[] seatingChart = (rs.getString(3)).toCharArray();
                theater.initialize(numRow, numCol, seatingChart);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return theater;
    }

    public void updateSeatingChart(char[] seatingChart, int showtimeId) {
        String query =
                "update seating_chart\n" +
                "set chart = ?\n" +
                "where seating_chart.showtimeid = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, new String (seatingChart));
            preparedStatement.setInt(2,showtimeId);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printAllShowings() {
        String query = "select\n" +
                "\ttime_format(time, '%h:%i %p') as Showtime,\n" +
                "    date_format(date, '%W, %M %d, %Y') as Show_Date,\n" +
                "    movies.title as Movie_Title,\n" +
                "    showtimes.theaterid as Theater_Rm,\n" +
                "    theaters.capacity as Seats_Available\n" +
                "from showtimes\n" +
                "left join movies on showtimes.movieid = movies.movieid\n" +
                "left join theaters on showtimes.theaterid = theaters.theaterid\n" +
                "order by date, time, title;";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            System.out.println("----------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%10s %30s %50s %12s %17s%n",
                    "SHOWTIME",
                    "DATE",
                    "MOVIE TITLE",
                    "THEATER RM",
                    "SEATS AVAILABLE"
            );
            while (rs.next()) {
                String showTime = rs.getString("Showtime");
                String showDate = rs.getString("Show_Date");
                String movieTitle = rs.getString("Movie_Title");
                int theaterRm = rs.getInt("Theater_Rm");
                int capacity = rs.getInt("Seats_Available");


                System.out.printf("%10s %30s %50s %12d %17d%n",
                        showTime,
                        showDate,
                        movieTitle,
                        theaterRm,
                        capacity);
            }
            System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        }
        catch(SQLException e) {
            System.out.println("Failed to create statement");
            e.printStackTrace();
        }
    }
}
