package stockanalyzer;

public class YahooDataRetrievalException extends Exception{
    public YahooDataRetrievalException(String errorMsg){
        super(errorMsg);
    }
}
