package helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DeviceHelper {

    /**
     * Чтение в потоке
     * @param command
     * @return
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static String executeSh(String command) throws IOException, ExecutionException, InterruptedException {
        Process p = Runtime.getRuntime().exec(command);
        FutureTask<String > future = new FutureTask<>(()->{
            return new BufferedReader(new InputStreamReader(p.getInputStream()))
                    .lines().map(Object::toString)
                    .collect(Collectors.joining("\n"));
        });
        new Thread(future).start();
        return future.get();
    }

    /**
     * Чтение стримом
     * @param command
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static String[] executeBashStreamApi(String command) throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec(command);
        String[] message = {""};
        new Thread(()->{
           new BufferedReader(new InputStreamReader(p.getInputStream())).lines().forEach(x->message[0] += x + "\n");}
        ).start();
         p.waitFor();
         return message;
    }

    /**
     * Колхоз
     * @param command
     * @return
     */
    public static String[] executeBash(String command) {
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        final String[] message = {""};

        new Thread(()->{
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while (true){
                try {
                    if ((line = input.readLine()) == null) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                message[0] += line + "\n";
            }
        }).start();
        try {
            p.waitFor();
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        return message;
    }
}
