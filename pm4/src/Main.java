import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        try {

            System.out.print("Masukkan nama file (misal: data.txt): ");
            String fileName = input.nextLine();

            File file = new File("src/" + fileName);

            if (!file.exists()) {
                System.out.println("File tidak ditemukan!");
                return;
            }

            List<String> allLines = new ArrayList<>();

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                allLines.add(line);
            }
            br.close();

            System.out.println("Jumlah baris dalam file: " + allLines.size());

            System.out.print("Ingin membagi file menjadi berapa bagian? ");
            int parts = input.nextInt();

            if (parts <= 0) {
                System.out.println("Jumlah bagian harus lebih dari 0.");
                return;
            }

            Queue<List<String>> queue = new LinkedList<>();

            int chunkSize = (int) Math.ceil(allLines.size() / (double) parts);

            int index = 0;
            for (int i = 0; i < parts; i++) {
                List<String> chunk = new ArrayList<>();

                for (int j = 0; j < chunkSize && index < allLines.size(); j++) {
                    chunk.add(allLines.get(index));
                    index++;
                }

                queue.add(chunk);
            }

            int partNumber = 1;
            while (!queue.isEmpty()) {
                List<String> chunk = queue.poll();

                String outputName = "output_part_" + partNumber + ".txt";
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));

                for (String l : chunk) {
                    writer.write(l);
                    writer.newLine();
                }

                writer.close();
                System.out.println("Berhasil membuat: " + outputName);
                partNumber++;
            }

            System.out.println("\nPemotongan file selesai!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
