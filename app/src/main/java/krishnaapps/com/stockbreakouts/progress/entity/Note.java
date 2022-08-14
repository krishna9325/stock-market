package krishnaapps.com.stockbreakouts.progress.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private float profitLoss;
    private String stockName;
    private String anyMistake;
    private String progress;
    private String imagePath;

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    private int profit;


    public Note(float profitLoss, String stockName, String anyMistake, String progress, String imagePath, int profit) {
        this.profit = profit;
        this.profitLoss = profitLoss;
        this.stockName = stockName;
        this.anyMistake = anyMistake;
        this.progress = progress;
        this.imagePath = imagePath;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getAnyMistake() {
        return anyMistake;
    }

    public void setAnyMistake(String anyMistake) {
        this.anyMistake = anyMistake;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public float getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(float profitLoss) {
        this.profitLoss = profitLoss;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }


}