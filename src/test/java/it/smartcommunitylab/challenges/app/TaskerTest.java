package it.smartcommunitylab.challenges.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TaskerTest {


    @Test
    public void assign_option_not_provided() {
        final Tasker tasker = new Tasker(null);
        assertThat(tasker.isAssignSpecialSingle()).isTrue();
        assertThat(tasker.isAssignStandardSingle()).isTrue();
        assertThat(tasker.isAssignStandardGroup()).isTrue();
    }

    @Test
    public void assign_option_is_provided_empty() {
        final Tasker tasker = new Tasker(new String[0]);
        assertThat(tasker.isAssignSpecialSingle()).isTrue();
        assertThat(tasker.isAssignStandardSingle()).isTrue();
        assertThat(tasker.isAssignStandardGroup()).isTrue();
    }

    @Test
    public void assign_option_contains_unsupported_value() {
        final Tasker tasker = new Tasker(new String[] {"not-supported-assignment"});
        assertThat(tasker.isAssignSpecialSingle()).isFalse();
        assertThat(tasker.isAssignStandardSingle()).isFalse();
        assertThat(tasker.isAssignStandardGroup()).isFalse();
    }

    @Test
    public void assign_option_contains_standardSingleChallenge() {
        final Tasker tasker = new Tasker(new String[] {"standardSingle"});
        assertThat(tasker.isAssignSpecialSingle()).isFalse();
        assertThat(tasker.isAssignStandardSingle()).isTrue();
        assertThat(tasker.isAssignStandardGroup()).isFalse();
    }

    @Test
    public void assign_option_contains_two_assignments() {
        final Tasker tasker = new Tasker(new String[] {"standardSingle", "standardGroup"});
        assertThat(tasker.isAssignSpecialSingle()).isFalse();
        assertThat(tasker.isAssignStandardSingle()).isTrue();
        assertThat(tasker.isAssignStandardGroup()).isTrue();
    }
}
