package th.or.nectec.tanrabad.survey.presenter.job.service.jsonentity;

import th.or.nectec.tanrabad.entity.Location;

import java.util.List;

public class JsonPolygon {

    private List<Location> boundary;
    private List<Location>[] holes;


    public JsonPolygon(List<Location> boundary, List<Location>[] holes) {
        this.boundary = boundary;
        this.holes = holes;
    }

    public List<Location> getBoundary() {
        return boundary;
    }

    public List<Location> getHole(int holeIndex) {
        return holes[holeIndex];
    }

    public int getHolesCount() {
        return holes.length;
    }

    public List<Location>[] getAllHoles() {
        return holes;
    }
}