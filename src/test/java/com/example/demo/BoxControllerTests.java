package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.boxdelivery.Repository.BoxRepository;
import com.example.boxdelivery.Repository.ItemRepository;

@SpringBootTest
@AutoConfigureMockMvc
class BoxControllerTests {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private ItemRepository itemRepository;

    // Creating objects for testing
    //private Box box;
    //private Item item;

    /* @BeforeEach
    void setup() {
        // Prepare test data
        boxRepository.deleteAll();
        itemRepository.deleteAll();
        
        box = new Box("BOX001", 500, 100, "IDLE");
        item = new Item("Item-1", 100, "ITEM1");
        
        boxRepository.save(box);
        itemRepository.save(item);
    } */

    @Test
    void testCreateBox() throws Exception {
        // Create a new Box
        //Box newBox = new Box("BOX002", 300, 80, "IDLE");

        mockMvc.perform(post("http://localhost:8080/boxes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"txref\":\"BOX002\",\"weightLimit\":300,\"batteryCapacity\":80,\"state\":\"IDLE\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.txref").value("BOX002"))
                .andExpect(jsonPath("$.weightLimit").value(300))
                .andExpect(jsonPath("$.batteryCapacity").value(80))
                .andExpect(jsonPath("$.state").value("IDLE"));
    }

    @Test
    void testGetBoxByTxref() throws Exception {
        // Get Box by txref
        mockMvc.perform(get("http://localhost:8080/boxes/BOX002"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.txref").value("BOX002"))
                .andExpect(jsonPath("$.weightLimit").value(300))
                .andExpect(jsonPath("$.batteryCapacity").value(80))
                .andExpect(jsonPath("$.state").value("IDLE"));
    }

    @Test
    void testLoadItemsIntoBox() throws Exception {
        // Load items into a box
        mockMvc.perform(put("http://localhost:8080/boxes/BOX001/load")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"name\":\"Item-1\",\"weight\":100,\"code\":\"ITEM1\"}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.txref").value("BOX002"))
                .andExpect(jsonPath("$.state").value("LOADING"));
    }

    @Test
    void testCheckLoadedItems() throws Exception {
        // Check loaded items in a box
        mockMvc.perform(get("http://localhost:8080/boxes/BOX002/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Item-1"))
                .andExpect(jsonPath("$[0].weight").value(100))
                .andExpect(jsonPath("$[0].code").value("ITEM1"));
    }

    @Test
    void testGetAvailableBoxesForLoading() throws Exception {
        // Get available boxes for loading (boxes that are in IDLE state and battery > 25%)
        mockMvc.perform(get("http://localhost:8080/boxes/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].txref").value("BOX002"))
                .andExpect(jsonPath("$[0].state").value("IDLE"));
    }

    @Test
    void testCheckBatteryLevelForBox() throws Exception {
        // Check battery level of a box
        mockMvc.perform(get("http://localhost:8080/boxes/BOX001/battery"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.txref").value("BOX002"))
                .andExpect(jsonPath("$.batteryCapacity").value(80));
    }

}
