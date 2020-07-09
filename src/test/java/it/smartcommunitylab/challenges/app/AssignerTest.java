package it.smartcommunitylab.challenges.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class AssignerTest {


    @Test
    public void assign_option_not_provided() {
        final Assigner assigner = new Assigner(null);
        assertThat(assigner.isAssignSpecialSingle()).isTrue();
        assertThat(assigner.isAssignStandardSingle()).isTrue();
        assertThat(assigner.isAssignStandardGroup()).isTrue();
    }

    @Test
    public void assign_option_is_provided_empty() {
        final Assigner assigner = new Assigner(new String[0]);
        assertThat(assigner.isAssignSpecialSingle()).isTrue();
        assertThat(assigner.isAssignStandardSingle()).isTrue();
        assertThat(assigner.isAssignStandardGroup()).isTrue();
    }

    @Test
    public void assign_option_contains_unsupported_value() {
        final Assigner assigner = new Assigner(new String[] {"not-supported-assignment"});
        assertThat(assigner.isAssignSpecialSingle()).isFalse();
        assertThat(assigner.isAssignStandardSingle()).isFalse();
        assertThat(assigner.isAssignStandardGroup()).isFalse();
    }

    @Test
    public void assign_option_contains_standardSingleChallenge() {
        final Assigner assigner = new Assigner(new String[] {"standardSingle"});
        assertThat(assigner.isAssignSpecialSingle()).isFalse();
        assertThat(assigner.isAssignStandardSingle()).isTrue();
        assertThat(assigner.isAssignStandardGroup()).isFalse();
    }

    @Test
    public void assign_option_contains_two_assignments() {
        final Assigner assigner = new Assigner(new String[] {"standardSingle", "standardGroup"});
        assertThat(assigner.isAssignSpecialSingle()).isFalse();
        assertThat(assigner.isAssignStandardSingle()).isTrue();
        assertThat(assigner.isAssignStandardGroup()).isTrue();
    }
}
