import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

class Process {
    private HashMap<String, List<String>> dictionaries;
    private File[] files;

    public Process(File[] files) {
        this.dictionaries = new HashMap<>();
        this.files = files;
    }

    public void analyse() {
        for (File f : files) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
                String text = bufferedReader.readLine();
                if (text != null) {
                    text = text.toLowerCase();
                    for (String s : text.split(" |,|/.|\\(|\\)|]|\\[|\\{|}/")) {
                        synchronized (dictionaries) {
                            if (dictionaries.containsKey(s)) {
                                if (!dictionaries.get(s).contains(f.getName())) {
                                    ArrayList<String> values = new ArrayList<>(dictionaries.get(s));
                                    values.add(f.getName());
                                    dictionaries.put(s, values);
                                }
                            } else {
                                dictionaries.put(s, List.of(f.getName()));
                            }
                        }
                    }
                }
            } catch (
                    Exception e) {
                System.out.println("error: " + e.getMessage());
            }
        }
    }

    public List<String> search(String input) {
        input = input.toLowerCase();
        List<String> exceptions = new ArrayList<>();
        List<String> atLeasts = new ArrayList<>();
        List<String> result = new ArrayList<>();
        List<String> absolouts = new ArrayList<>();
        for (String s : input.split(" ")) {
            if (s.charAt(0) == '-') {
                if (dictionaries.get(s.substring(1)) != null) {
                    exceptions.addAll(dictionaries.get(s.substring(1)));
                }
            } else if (s.charAt(0) == '+') {
                if (dictionaries.get(s.substring(1)) != null) {
                    atLeasts.addAll(dictionaries.get(s.substring(1)));
                }
            } else {
                if (dictionaries.get(s) != null) {
                    absolouts.addAll(dictionaries.get(s));
                }
            }

        }
        if (absolouts.size() != 0 && atLeasts.size() != 0) {
            absolouts.retainAll(atLeasts);
        } else {
            result.addAll(absolouts);
            result.addAll(atLeasts);
        }
        result.removeAll(exceptions);
        return result;
    }
}