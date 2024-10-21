package atlantafx.sampler.admin.page.components;

import atlantafx.sampler.admin.page.OutlinePage;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class HomePage extends OutlinePage {
    public static final String NAME = "Home";

    @Override
    public String getName() {
        return NAME;
    }

    public HomePage() {
        super();
        initialize();
    }

    private void initialize() {
        addPageHeader();
        addWelcomeSection();
    }

    private void addWelcomeSection() {
        StackPane welcomeSection = new StackPane();
        welcomeSection.getStyleClass().add("welcome-section");
        welcomeSection.getChildren().add(new Label("Welcome to BLueOPS!"));
        getChildren().add(welcomeSection);
    }

    @Override
    protected void onRendered() {
        // Any additional setup after the page is rendered
        super.onRendered();
        System.out.println("HomePage rendered successfully.");
    }
}
