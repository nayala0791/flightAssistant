public class Requests {

    private String seat;
    private String name;
    private int time;
    private String order;

    public Requests(){
        this.seat = "";
        this.name = "";
        this.time = 0;
        this.order = "";
    }

    public Requests(String seat, String name, int time, String order){
        this.seat = seat;
        this.name = name;
        this.time = time;
        this.order = order;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String urgency) {
        this.order = urgency;
    }
}
