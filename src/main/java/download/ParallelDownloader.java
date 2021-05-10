package download;

import stockanalyzer.YahooDataRetrievalException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelDownloader extends Downloader{

    @Override
    public int process(List<String> urls) throws YahooDataRetrievalException{
        int count = 0;
        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService parallelExecutor = Executors.newFixedThreadPool(threads);

        List<Future<String>> parallelDownload = new ArrayList<>();

        for(String url: urls){
            parallelDownload.add(parallelExecutor.submit(()->saveJson2File(url)));
        }

        for(Future<String> future : parallelDownload){
            try {
                String filename = future.get();
                if(filename != null){
                    count++;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                throw new YahooDataRetrievalException("Something went wrong with multithreading!");
            }
        }

        return count;
    }
}
