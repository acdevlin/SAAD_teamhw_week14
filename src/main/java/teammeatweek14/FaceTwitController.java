package teammeatweek14;

import javax.inject.Inject;

import org.springframework.social.connect.ConnectionRepository;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class FaceTwitController {
	private Facebook facebook;
	private Twitter twitter;
	private ConnectionRepository connectionRepository;

    @Inject
    public FaceTwitController(Facebook facebook, Twitter twitter, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.twitter = twitter;
		this.connectionRepository = connectionRepository;
    }

    @RequestMapping(method=RequestMethod.GET)
    public String helloFaceTwit(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) != null) {
            model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());
            PagedList<Post> feed = facebook.feedOperations().getFeed();
            model.addAttribute("feed", feed);
        }

        if (connectionRepository.findPrimaryConnection(Twitter.class) != null) {
            model.addAttribute(twitter.userOperations().getUserProfile());
            CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends();
            model.addAttribute("friends", friends);
        }

        return "homePage";
    }
}
