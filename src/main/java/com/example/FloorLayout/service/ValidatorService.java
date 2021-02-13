package com.example.FloorLayout.service;

import com.example.FloorLayout.domain.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidatorService {


    @Autowired
    private LayoutService layoutService;
    @Autowired
    private ValidatorService validatorService;

    public boolean isDataCorrect(String data) {
        String[] list = data.split(",");

        if (list.length % 2 != 0 || list.length < 3) {
            return false;
        }

        return true;
    }

    public boolean isDiagonalPresent(String data) {
        String[] list = data.split(", ");
        String[] xy1;
        String[] xy2;

        for (int i = 0; i < list.length; i++) {
            xy1 = list[i].split(" ");

            if (i != list.length - 1) {
                xy2 = list[i + 1].split(" ");
            } else {
                xy2 = list[0].split(" ");
            }

            if (!xy1[0].equals(xy2[0]) && !xy1[1].equals(xy2[1])) {
                return true;
            }
        }

        return false;
    }

    public boolean isRepeatingCornerPresent(String data){
        List<Point> points = layoutService.getXY(data.replaceAll(", "," "));

        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                if  (points.get(i).equals(points.get(j))) return true;
            }
        }
        return false;
    }


    public boolean isCoordinateGoingClockwise(String data) {
        String[] list = data.split(", ");
        ArrayList<String> x = new ArrayList<>();
        ArrayList<String> y = new ArrayList<>();

        for (int i = 0; i < list.length; i++) {
            String[] pair = list[i].split(" ");
            x.add(pair[0]);
            y.add(pair[1]);
        }

        int dx1 = Integer.parseInt(x.get(1)) - Integer.parseInt(x.get(0));
        int dx2 = Integer.parseInt(x.get(2)) - Integer.parseInt(x.get(1));
        int dy1 = Integer.parseInt(x.get(1)) - Integer.parseInt(x.get(0));
        int dy2 = Integer.parseInt(x.get(2)) - Integer.parseInt(x.get(1));

        double r = dx1 * dy2 - dx2 * dy1;;

        if ( r < 0 ) return false;
        else return true;
    }
    public boolean validate(@RequestParam String points, Model model) {

        if (!validatorService.isDataCorrect(points)) {
            model.addAttribute("error", "The data is incorrect");
            return true;
        }

        if (validatorService.isRepeatingCornerPresent(points)) {
            model.addAttribute("error", "The corners are repeated");
            return true;
        }
        if (!validatorService.isCoordinateGoingClockwise(points)) {
            model.addAttribute("error", "The points ar going counterclockwise");
            return true;
        }
        if (validatorService.isDiagonalPresent(points)) {
            model.addAttribute("error", "The walls are diagonal");
            return true;
        }

        return false;
    }

}
