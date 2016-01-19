package com.example;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Collection;


@SpringBootApplication
@Controller
@EnableAutoConfiguration
public class KbcApplication {

	@Autowired
        private ReservationRepository reservationRepository;

	public static void main(String[] args) {
		SpringApplication.run(KbcApplication.class, args);
        }
	
	@RequestMapping("/hello")
        String home(Model model) {
	     model.addAttribute("reservations", this.reservationRepository.findAll());
             return "reservations";
        }

	@Bean
        CommandLineRunner init(ReservationRepository rr) {
          return args -> {
            Arrays.asList("Pierre2,Josh,Amos,Paolo,Danny,Chris,Floyd".split(","))
                    .forEach(n -> rr.save(new Reservation(n)));
	};
     }
}

@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation, Long> {
}


@Entity
class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    private String reservationName;

    Reservation() {
    }

    public Reservation(String reservationName) {
        this.reservationName = reservationName;
    }

    public String getReservationName() {
        return reservationName;
    }

    public Long getId() {
        return id;
    }
}

