package es.upm.miw.bantumi.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StorageFiles {

    private Context context;

    public StorageFiles(Context context) {
        this.context = context;
    }

    public void save(String fileName, String text) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void update(String fileName, String text) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_APPEND);
            fos.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String loadToString(String fileName) {
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public String[] getAllLines(String filename) {
        List<String> lines = new ArrayList<>();
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String text;
            while ((text = br.readLine()) != null) {
                lines.add(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return lines.toArray(new String[0]);
    }

    public void deleteLine(String fileName, String text) {
        String updatedText = this.loadToString(fileName);
        updatedText = updatedText.replace(text, "");
        updatedText = updatedText.replace("\n\n", "\n");
        if (updatedText.charAt(0) == '\n') {
            updatedText = updatedText.replaceFirst("\n", "");
        }
        this.save(fileName, updatedText);
    }
}