import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class History {
    private String request;
    private int maxResult;
    private int days;

    static List<History> userHistory = new ArrayList<>();
    static ListIterator<History> iterator;

    public History(String request, int maxResult, int days) {
        this.request = request;
        this.maxResult = maxResult;
        this.days = days;
    }

    public History() {
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public List<History> getUserHistory() {
        return userHistory;
    }

    public void setUserHistory(List<History> userHistory) {
        this.userHistory = userHistory;
    }

    public History getPreviousRequest() {
        if (iterator.hasPrevious()) {
            return iterator.previous();
        } else {
            System.out.println("It is the first request");
        }
        return iterator.previous();
    }

    public History getNextRequest() {
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            System.out.println("It is the last request");
        }
        return iterator.next();
    }

    //Записывает ArrayList в файл с назаванием History.json
    public void gsonToFile(ArrayList<?> history) {
        File fileOfWorkers = new File("History.json");
        if (!fileOfWorkers.exists()) {
            try {
                fileOfWorkers.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (Writer writer = new FileWriter(fileOfWorkers)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(history, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile(String pathname) throws IOException {
        File file = new File(pathname);
        StringBuilder fileContents = new StringBuilder((int) file.length());

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + System.lineSeparator());
            }

            return fileContents.toString();
        }
    }

    public static <T> T fromJSON(final TypeReference<T> type,
                                 final String jsonPacket) {
        T data = null;
        try {
            data = new ObjectMapper().readValue(jsonPacket, type);
        } catch (Exception e) {
            //
        }

        return data;
    }

    //Фунция переводит итератор в конец
    public void iteratorToTheEndOfList(ArrayList<History> histories) {
        iterator = histories.listIterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
    }

    //Тут мы запиываем наши праметры, которые вводит пользователь
    public void writeToFile(String request, int maxResult, int days) {
        userHistory.add(new History(request, maxResult, days));
        gsonToFile((ArrayList<?>) userHistory);
    }

    //Надо вызвать, там где будут работать со стрелочками
    public void read() throws IOException {
        String userHistoryRead = readFile("History.json");
        List<History> histories = fromJSON(new TypeReference<ArrayList<History>>() {
        }, userHistoryRead);
        iterator = histories.listIterator();
        iteratorToTheEndOfList((ArrayList<History>) histories);
    }

    public History arrows(boolean leftOrRight) {
        // true = "->", false = "<-"
        return leftOrRight ? getNextRequest() : getPreviousRequest();
    }

    @Override
    public String toString() {
        return "History -> " +
                "request = '" + request + '\'' +
                ", maxResult = " + maxResult +
                ", days = " + days;
    }
}
