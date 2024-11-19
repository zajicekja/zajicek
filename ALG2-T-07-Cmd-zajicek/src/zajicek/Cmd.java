package zajicek;

import java.io.File;
import java.io.IOException;

public class Cmd {

    private File actualDir;

    public Cmd() {
        actualDir = new File(System.getProperty("user.dir"));
    }

    public String getActualDir() throws IOException {
        return actualDir.getCanonicalPath();
    }

    public String help() {
        return "help                vrátí víceřádkový textový řetězec s nápovědou" + "\n"
                + "dir                 vrátí víceřádkový textový řetězec s formátovaným výpisem aktuálního adresáře" + "\n"
                + "dir <adresář>       vrátí víceřádkový textový řetězec s formátovaným výpisem adresáře předaného v parametru, nemění aktuální adresář" + "\n"
                + "cd <adresář>        změna aktuálního adresáře dle parametru (parametr může představovat absolutní nebo i relativní cestu)" + "\n"
                + "mkfile <soubor>     vytvoří nový soubor, jehož jméno bylo zadáno v parametru" + "\n"
                + "mkdir <adresář>     vytvoří zadaný adresář" + "\n"
                + "mkdirs <cesta>      vytvoří adresáře odpovídající cestě zadané v parametru" + "\n"
                + "rename <jm1> <jm2>  přejmenuje soubor nebo adresář" + "\n"
                + "exit                konec aplikace";
    }

    public void cd(String dir) {
        File newActualDir = new File(actualDir, dir);
        if (newActualDir.isDirectory()) {
            actualDir = new File(actualDir, dir);
        } else if (newActualDir.exists()) {
            throw new IllegalArgumentException("Název adresáře je neplatný.");
        } else {
            throw new IllegalArgumentException("Systém nemůže nalézt uvedenou cestu.");
        }
    }

    public void mkfile(String file) throws IOException {
        File f = new File(actualDir, file);
        f.createNewFile();
    }

    public String dir() {
        File[] listFile;
        listFile = actualDir.listFiles();

        StringBuilder sb = new StringBuilder();
        for (File file : listFile) {
            sb.append(file.isDirectory() ? "<DIR>  " : "       ").append(file.getName()).append("\n");

        }
        return sb.toString();
    }

    public String recursiveDir(String directory, int level) {
        StringBuilder sb = new StringBuilder();
        File dir = new File(directory);

        if (!dir.exists() || !dir.isDirectory()) {
            return "Invalid directory";
        }

        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                for (int i = 0; i < level; i++) {
                    sb.append("\t");
                }
                sb.append(file.isDirectory() ? "<DIR> " : "      ").append(file.getName()).append("\n");
                if (file.isDirectory()) {
                    sb.append(recursiveDir(file.getAbsolutePath(), level + 1));
                }
            }
        }

        return sb.toString();
    }

}
