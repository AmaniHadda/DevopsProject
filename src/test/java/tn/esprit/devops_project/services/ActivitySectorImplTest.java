package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.entities.ActivitySector;
import tn.esprit.devops_project.repositories.ActivitySectorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ActivitySectorImplTest {
    @Autowired
    @Mock
    ActivitySectorRepository activitySectorRepository;
    @Autowired
    @InjectMocks
    ActivitySectorImpl activitySector;
    ActivitySector actSector=new ActivitySector(3L,"l4", "aa", null);
    List<ActivitySector> listeActivity = new ArrayList<ActivitySector>() {
        {
            add(new ActivitySector(1L,"bb","cc", null));
            add(new ActivitySector(2L, "l3", "bb", null));
        }
    };

    @Test
    void retrieveAllActivitySectors() {
        Mockito.when(activitySectorRepository.findAll()).thenReturn(listeActivity);
        List<ActivitySector>liste=activitySector.retrieveAllActivitySectors();
        Assertions.assertNotNull(liste);
    }

    @Test
    void addActivitySector() {
        ActivitySector activitySector1=new ActivitySector(1L,"bb","cc", null);
        Mockito.when(activitySectorRepository.save(activitySector1)).thenReturn(activitySector1);
        ActivitySector activitySectorResult=activitySector.addActivitySector(activitySector1);
        Assertions.assertEquals(activitySectorResult, activitySector1);

    }

    @Test
    void deleteActivitySector() {
        Mockito.doNothing().when(activitySectorRepository).deleteById(Mockito.anyLong());
        activitySector.deleteActivitySector(actSector.getIdSecteurActivite());
        Mockito.verify(activitySectorRepository, Mockito.times(1)).deleteById(actSector.getIdSecteurActivite());
    }

    @Test
    void updateActivitySector() {
        /*
        ActivitySector actSectorUpdated=new ActivitySector(3L,"test", "test", null);
        Mockito.when(activitySectorRepository.save(actSectorUpdated)).thenReturn(actSectorUpdated);
        ActivitySector activitySectorResult=activitySector.updateActivitySector(actSectorUpdated);
        Assertions.assertEquals(activitySectorResult, actSectorUpdated);
        */
        Mockito.when(activitySectorRepository.save(Mockito.any(ActivitySector.class))).thenReturn(actSector);
        ActivitySector activitySectorResult=activitySector.updateActivitySector(actSector);
        Mockito.verify(activitySectorRepository, Mockito.times(1)).save(actSector);
        assertEquals(actSector, activitySectorResult);

    }

    @Test
    void retrieveActivitySector() {
        Mockito.when(activitySectorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(actSector));
        ActivitySector activitySector1=activitySector.retrieveActivitySector(3L);
        Assertions.assertNotNull(activitySector1);
    }
}