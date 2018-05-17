package com.shaffer.ad340assignments.viewmodels;

import com.google.firebase.database.DataSnapshot;
import com.shaffer.ad340assignments.datamodels.MatchesModel;
import com.shaffer.ad340assignments.models.MatchItem;

import java.util.ArrayList;
import java.util.function.Consumer;

public class MatchesViewModel {

    private MatchesModel matchesModel;

    public MatchesViewModel() {
        matchesModel = new MatchesModel();
    }

    public void addmatchItem(MatchItem item) {
        matchesModel.addMatchItem(item);
    }

    public void getMatchItems(Consumer<ArrayList<MatchItem>> responseCallback) {
        matchesModel.getMatchItems(
                (DataSnapshot dataSnapshot) -> {
                    ArrayList<MatchItem> matchItems = new ArrayList<>();
                    for (DataSnapshot matchSnapshot : dataSnapshot.getChildren()) {
                        MatchItem item = matchSnapshot.getValue(MatchItem.class);
                        assert item != null;
                        item.uid = matchSnapshot.getKey();
                        matchItems.add(item);
                    }
                    responseCallback.accept(matchItems);
                },
                (databaseError -> System.out.println("Error reading Match Items: " + databaseError))
        );
    }

    public void updateMatchItem(MatchItem item) {
        matchesModel.updateMatchItemById(item);
    }

    public void clear() {
        matchesModel.clear();
    }
}
