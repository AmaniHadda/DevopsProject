package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.entities.ActivitySector;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.entities.SupplierCategory;
import tn.esprit.devops_project.repositories.ActivitySectorRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
public class ActivitySectorImplTestJunit {
    @Autowired
    ActivitySectorRepository activitySectorRepository;
    @Autowired
    ActivitySectorImpl activitySector;
    @Test
    void retrieveAllActivitySectors() {
        ActivitySector activitySector1 = new ActivitySector(1L, "activity1", "act", null);
        activitySectorRepository.save(activitySector1);
        ActivitySector activitySector2 = new ActivitySector(2L, "activity2", "act2", null);
        activitySectorRepository.save(activitySector2);
        List<ActivitySector> activitySectors = activitySector.retrieveAllActivitySectors();
        assertEquals(2, activitySectors.size());
        assertEquals("activity1", activitySectors.get(0).getCodeSecteurActivite());
        assertEquals("act", activitySectors.get(0).getLibelleSecteurActivite());
        assertEquals("activity2", activitySectors.get(1).getCodeSecteurActivite());
        assertEquals("act2", activitySectors.get(1).getLibelleSecteurActivite());
    }
    @Test
    void addActivitySector() {
        ActivitySector newActivity = new ActivitySector(1L, "activity1", "act", null);
        ActivitySector addedActivitySector = activitySector.addActivitySector(newActivity);
        ActivitySector retrievedActivitySector = activitySectorRepository.findById(addedActivitySector.getIdSecteurActivite()).orElse(null);
        assertNotNull(addedActivitySector.getIdSecteurActivite());
        assertEquals("activity1", retrievedActivitySector.getCodeSecteurActivite());
        assertEquals("act", retrievedActivitySector.getLibelleSecteurActivite());
        assertNotNull(retrievedActivitySector);
        assertEquals(addedActivitySector.getIdSecteurActivite(), retrievedActivitySector.getIdSecteurActivite());
        assertEquals(addedActivitySector.getCodeSecteurActivite(), retrievedActivitySector.getCodeSecteurActivite());
        assertEquals(addedActivitySector.getLibelleSecteurActivite(), retrievedActivitySector.getLibelleSecteurActivite());
    }
    @Test
    void deleteActivitySector() {
        ActivitySector activitySector1 = new ActivitySector(1L, "activity1", "act", null);
        activitySector1 = activitySectorRepository.save(activitySector1);
        Long activitySectorId = activitySector1.getIdSecteurActivite();
        activitySector.deleteActivitySector(activitySectorId);
        ActivitySector deletedActivitySector = activitySectorRepository.findById(activitySectorId).orElse(null);
        assertNull(deletedActivitySector);
        assertFalse(activitySectorRepository.existsById(activitySectorId));
    }
    @Test
    void updateActivitySector() {
        ActivitySector activitySector1 = new ActivitySector(1L, "activity1", "act", null);
        activitySectorRepository.save(activitySector1);
        activitySector1.setCodeSecteurActivite("UpdatedCode");
        activitySector1.setLibelleSecteurActivite("UpdatedLabel");
        ActivitySector updatedActivitySector = activitySector.updateActivitySector(activitySector1);
        ActivitySector retrievedActivitySector = activitySectorRepository.findById(updatedActivitySector.getIdSecteurActivite()).orElse(null);
        assertNotNull(retrievedActivitySector);
        assertEquals("UpdatedCode", retrievedActivitySector.getCodeSecteurActivite());
        assertEquals("UpdatedLabel", retrievedActivitySector.getLibelleSecteurActivite());
    }
    @Test
    void retrieveActivitySector() {
        ActivitySector activitySector1 = new ActivitySector(1L, "activity1", "act", null);
        activitySector1 = activitySectorRepository.save(activitySector1);
        Long activitySectorId = activitySector1.getIdSecteurActivite();
        ActivitySector retrievedActivitySector = activitySector.retrieveActivitySector(activitySectorId);
        assertNotNull(retrievedActivitySector);
        assertEquals("activity1", retrievedActivitySector.getCodeSecteurActivite());
        assertEquals("act", retrievedActivitySector.getLibelleSecteurActivite());
    }
}
