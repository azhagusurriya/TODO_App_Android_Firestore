package viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.todoandroid.repositories.ChecklistRepository;

import models.Checklist;

public class ChecklistViewModel extends ViewModel {

    private final String TAG = this.getClass().getCanonicalName();
    private static final ChecklistViewModel ourInstance = new ChecklistViewModel();
    private final ChecklistRepository checklistRepository = new ChecklistRepository();

    public static ChecklistViewModel getInstance(){
        return ourInstance;
    }

    public ChecklistRepository getChecklistRepository(){
        return checklistRepository;
    }

    private ChecklistViewModel(){}

    public void addCheckListItem(String userID, Checklist checklist){
        this.checklistRepository.addChecklistItem(userID, checklist);
    }

    public void getAllChecklistItems(String userID){
        this.checklistRepository.getAllChecklistItems(userID);
    }

    public void deleteChecklistItem(String userID, String checklistID){
        this.checklistRepository.deleteChecklist(userID, checklistID);
    }

}
