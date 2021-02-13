package com.example.FloorLayout.service;

import com.example.FloorLayout.domain.Point;
import com.example.FloorLayout.domain.Layout;
import com.example.FloorLayout.repo.LayoutRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LayoutService {

    @Autowired
    private LayoutRepo layoutRepo;

    public List<Layout> findAll() {
        return (List<Layout>) layoutRepo.findAll();
    }

    public void saveLayout(String name, String coordinates) {
        Layout layout = new Layout(name, coordinates);
        layoutRepo.save(layout);
    }

    public Layout findLayoutById(Integer id) {
        return layoutRepo.findLayoutById(id);
    }

    public void deleteLayout(Integer id) {
        layoutRepo.deleteById(id);
    }

    public List<Point> getXY(String coordinates) {
        List<Point> list = new ArrayList<>();

        String[] arrayList = coordinates.split(" ");

        try {
            for (int i = 0; i < arrayList.length; i = i + 2) {
                Point c = new Point(Integer.parseInt(arrayList[i]), Integer.parseInt(arrayList[i + 1]));
                list.add(c);
            }
        } catch (Exception e) {
            list = null;
        }
        return list;
    }

    public Iterable<Layout> filter(String filter) {
        Iterable<Layout> layouts;
        if (filter != null && !filter.isEmpty()) {
            layouts = layoutRepo.findByName(filter);
        } else {
            layouts = layoutRepo.findAll();
        }
        return layouts;
    }
}
