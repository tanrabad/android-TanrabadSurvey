package th.or.nectec.tanrabad.survey.presenter;

import th.or.nectec.tanrabad.entity.lookup.ContainerType;
import th.or.nectec.tanrabad.survey.R;

import java.util.HashMap;

public class ContainerIconMapping {
    HashMap<Integer, Integer> containerIconMapper = new HashMap<>();

    public ContainerIconMapping() {
        containerIconMapper.put(1, R.mipmap.ic_container_earthen_jar);
        containerIconMapper.put(2, R.mipmap.ic_container_bottle);
        containerIconMapper.put(3, R.mipmap.ic_container_vase);
        containerIconMapper.put(4, R.mipmap.ic_container_ant_tray);
        containerIconMapper.put(5, R.mipmap.ic_container_pot_saucer);
        containerIconMapper.put(6, R.mipmap.ic_container_lotus);
        containerIconMapper.put(7, R.mipmap.ic_container_tire);
        containerIconMapper.put(8, R.mipmap.ic_container_leaf);
        containerIconMapper.put(9, R.mipmap.ic_container_garbages);
        containerIconMapper.put(10, R.mipmap.ic_container_bowl);
    }

    public int getContainerIcon(ContainerType containerType) {
        if (!containerIconMapper.containsKey(containerType.getId()))
            return R.mipmap.ic_building_home_black;
        return containerIconMapper.get(containerType.getId());
    }
}