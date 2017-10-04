package controllers.api;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import controllers.AssetsBuilder;
import play.api.http.HttpErrorHandler;
import play.api.mvc.Action;
import play.api.mvc.AnyContent;

/**
 * Created by pedropires on 1/23/17.
 */
@Singleton
public class Assets extends AssetsBuilder {

    @Inject
    public Assets(HttpErrorHandler errorHandler) {
        super(errorHandler);
    }

    public Action<AnyContent> at(String path, String file) {
        return super.at(path, file, false);
    }
}
