package services;

import model.StatPDO;

import java.util.List;

public class ClientService {
    public void sendStats(List<List<StatPDO>> list) {
        HttpClientManager.post("/stats/add", StatPDO.toJSON(list));
    }
}
