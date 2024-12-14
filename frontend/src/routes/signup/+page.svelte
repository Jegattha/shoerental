<script>
  import { isAuthenticated, user } from "../../store";
  import auth from "../../auth.service";

  let email = "";
  let password = "";
  let firstName = "";
  let lastName = "";
  let userTypes = ["mieter", "vermieter"];
let selectedUserType = "";
  let signupForm;

  $: if ($isAuthenticated) {
    // reset 
    email = "";
    password = "";
  }
  function signup() {
    // form validation with bootstrap: see https://getbootstrap.com/docs/5.3/forms/validation/
    if (signupForm.checkValidity()) {
      auth.signup(email, password, firstName, lastName, selectedUserType);
    }
    signupForm.classList.add("was-validated");
  }
</script>

{#if $isAuthenticated}
  <h1>Welcome {$user.nickname}!</h1>
  <p>Herzlichen Dank für deine Mitgliedschaft bei Shoerental! Wir freuen uns, dich als neues Mitglied in unserer wachsenden Familie begrüßen zu dürfen. Mit uns hast du nicht nur stilvolle Damen- und atemberaubende Herrenschuhe zur Verfügung, sondern auch exklusiven Zugang zu einer Welt voller modischer Möglichkeiten.

    Du bist jetzt Teil einer Gemeinschaft von Menschen, die die Freiheit und den Stil lieben, den das Mieten von exquisiter Schuhe bietet. Bei Shoerental verstehen wir, dass Individualität und Eleganz Hand in Hand gehen. Unsere Auswahl an Damen- und Herrenschuhe ermöglicht es dir, deinen persönlichen Stil zu unterstreichen und bei jedem Anlass zu strahlen.</p>
    

{:else}
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card">
          <div class="card-header">Sign up</div>
          <div class="card-body">
            <form
              on:submit|preventDefault={signup}
              bind:this={signupForm}
              class="needs-validation"
              novalidate
            >
              <div class="mb-3">
                <label for="username" class="form-label">E-Mail</label>
                <input
                  bind:value={email}
                  type="email"
                  class="form-control"
                  id="username"
                  name="username"
                  required
                />
                <div class="invalid-feedback">
                  Please provide an e-mail address.
                </div>
              </div>
              <div class="mb-3">
                <label for="first-name" class="form-label">First Name</label>
                <input
                  bind:value={firstName}
                  type="text"
                  class="form-control"
                  id="first-name"
                  name="first-name"
                />
              </div>
              <div class="mb-3">
                <label for="last-name" class="form-label">Last Name</label>
                <input
                  bind:value={lastName}
                  type="text"
                  class="form-control"
                  id="last-name"
                  name="last-name"
                />
              </div>
              <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input
                  bind:value={password}
                  type="password"
                  class="form-control"
                  id="password"
                  name="password"
                  required
                />
                <div class="invalid-feedback">Please choose a password.</div>
              </div>
              <div class="mb-3">
                <label for="userType" class="form-label">User Type</label>
                <select
                bind:value={selectedUserType}
                class="form-select"
                required
                >
                {#each userTypes as userType}
                <option value={userType}>{userType}</option>
                {/each}
                </select>
                <div class="invalid-feedback">Please select a user type.</div>
                </div>
              <button type="submit" class="btn btn-primary">Sign up</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
{/if}
