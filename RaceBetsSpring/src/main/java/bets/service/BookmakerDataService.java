package bets.service;

import bets.dto.request.RaceRequest;
import bets.dto.response.AdminStatsResponse;
import bets.dto.response.BookmakerIncomeStatForDay;
import bets.dto.response.BookmakerIncomeStats;
import bets.entity.BookmakerData;
import bets.entity.Race;
import bets.entity.User;
import bets.repo.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookmakerDataService {
    private final UserRepo userRepo;
    private final BookmakerDataRepo bookmakerDataRepo;
    public BookmakerDataService( UserRepo userRepo, BookmakerDataRepo bookmakerDataRepo) {
        this.userRepo = userRepo;
        this.bookmakerDataRepo = bookmakerDataRepo;
    }

    public AdminStatsResponse getStats(String authorization) {
        AdminStatsResponse resp=new AdminStatsResponse();
        resp.setStats( new ArrayList<>(  ) );
        List<User> users=userRepo.findAll();
        for ( User user:users ) {
            List<BookmakerData> data=bookmakerDataRepo.findAllByBookmaker( user );
            if(data.size()==0){
                continue;
            }
            BookmakerIncomeStats bookmaker_stats=new BookmakerIncomeStats();
            bookmaker_stats.setName( user.getUsername() );
            bookmaker_stats.setBookmakerStats( new ArrayList<>(  ) );
            for ( BookmakerData current_data:data ) {
                BookmakerIncomeStatForDay day_stats=new BookmakerIncomeStatForDay();
                day_stats.setDate( current_data.getDate().toString() );
                day_stats.setIncome( current_data.getIncome() );
                bookmaker_stats.getBookmakerStats().add(day_stats);
            }
            resp.getStats().add(bookmaker_stats);
        }
        return resp;
    }
}
