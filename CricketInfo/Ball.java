public class Ball {
    private final int ballNumber;
    private final String bowlerId;
    private final String batsmanId;
    private final String run;

    public Ball(int ballNum, String bowlerId, String batsmanId, String run) {
        this.ballNumber = ballNum;
        this.bowlerId = bowlerId;
        this.batsmanId = batsmanId;
        this.run = run;
    }

    public int getBallNumber() {
        return ballNumber;
    }

    public String getBatsmanId() {
        return batsmanId;
    }

    public String getBowlerId() {
        return bowlerId;
    }

    public String getRun() {
        return run;
    }

}
