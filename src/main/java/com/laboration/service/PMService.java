package com.laboration.service;

import com.laboration.DTO.PMWrapper;
import com.laboration.models.PersonalMessage;
import com.laboration.models.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PMService extends Services{
    @Transactional
    public PMWrapper createPM(PMWrapper pm){
        if(pm != null){
            Date date = Calendar.getInstance().getTime();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = format.format(date);
            pm.setDateSent(strDate);
            pmRepo.save(object.mapper.map(pm, PersonalMessage.class));
        }
        return pm;
    }

    public List<PMWrapper> getPMsFromSender(String sendername, String receivername){
        List<PersonalMessage> listOfPms = pmRepo.findAllBySenderAndReceiver(userRepo.findByUsername(sendername), userRepo.findByUsername(receivername));
        if(sendername.compareTo(receivername) != 0){
            List<PersonalMessage> listOfPms2 = pmRepo.findAllBySenderAndReceiver(userRepo.findByUsername(receivername),userRepo.findByUsername(sendername));
            listOfPms.addAll(listOfPms2);
        }
        List<PMWrapper> listOfMsg = new ArrayList<PMWrapper>();
        for(int i = 0; i < listOfPms.size(); i++){
            listOfMsg.add(object.mapper.map(listOfPms.get(i), PMWrapper.class));
        }
        listOfMsg.sort(new Comparator<PMWrapper>() {
            @Override
            public int compare(PMWrapper o1, PMWrapper o2) {
                return o2.getDateSent().compareTo(o1.getDateSent());
            }
        });
        return listOfMsg;
    }

    public PMWrapper getSpecificPM(int pmId) {
        PersonalMessage msg;
        try{
            msg = pmRepo.getOne(Long.valueOf(pmId));
        }catch(Exception e){
            e.printStackTrace();
            return new PMWrapper();
        }
        return object.mapper.map(msg, PMWrapper.class);
    }


}


