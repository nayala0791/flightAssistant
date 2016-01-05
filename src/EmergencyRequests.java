
public class EmergencyRequests {

    private String name;
    private String seat;


    public EmergencyRequests(){
        name = "";
        seat = "";
    }

    public EmergencyRequests(String seat, String name){
        this.name = name;
        this.seat = seat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}
