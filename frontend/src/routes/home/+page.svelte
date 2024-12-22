<script>
    import { isAuthenticated, user } from "../../store";
    import auth from "../../auth.service";

    let username = "";
    let password = "";
    let loginForm;

    $: if ($isAuthenticated) {
    // reset the input values as soon as the user is authenticated.
    username = "";
    password = "";
  }


    function loginWithUsernameAndPassword() {
        // form validation with bootstrap: see https://getbootstrap.com/docs/5.3/forms/validation/
        if (loginForm.checkValidity()) {
            console.log("login");
            auth.login(username, password);
        }
        loginForm.classList.add("was-validated");
    }
</script>

{#if $isAuthenticated}
    <h1>Welcome {$user.nickname}!</h1>
    <!-- Beispiel für die Verwendung von Bildern im Ordner 'static/images' -->
    <p>
        Herzlich willkommen bei Shoerental! Wir möchten uns herzlich bei dir bedanken, dass du ein Teil unserer wachsenden Familie geworden bist. Dein Engagement, Schuhe zu mieten statt zu kaufen, verdient besondere Anerkennung. Vielen Dank, dass du nicht nur deinen eigenen Stil findest, sondern auch dazu beiträgst, Modeabfälle zu reduzieren und einen nachhaltigen Lebensstil zu fördern. Deine ökologische Denkweise und deine bewusste Entscheidung tragen dazu bei, unsere Welt zu einem besseren Ort zu machen. Bei Shoerental erwarten dich wunderschöne Frauen- und Männerschuhe, um deinen Stil zu unterstreichen. Nochmals danke, dass du Teil unserer Familie bist – wir freuen uns darauf, gemeinsam mit dir modische und nachhaltige Wege zu erkunden!</p>
      
    <img src="/images/vite.svg" alt="Vite Logo" />        
{:else}
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">Login</div>
                    <div class="card-body">
                        <form
                            on:submit|preventDefault={loginWithUsernameAndPassword}
                            bind:this={loginForm}
                            class="needs-validation"
                            novalidate
                        >
                            <div class="mb-3">
                                <label for="username" class="form-label"
                                    >Email</label
                                >
                                <input
                                    bind:value={username}
                                    type="text"
                                    class="form-control"
                                    id="username"
                                    name="username"
                                    required
                                />
                                <div class="invalid-feedback">
                                    Please provide your username.
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label"
                                    >Password</label
                                >
                                <input
                                    bind:value={password}
                                    type="password"
                                    class="form-control"
                                    id="password"
                                    name="password"
                                    required
                                />
                                <div class="invalid-feedback">
                                    Please provide your password.
                                </div>
                            </div>
                            <div class="row align-items-end">
                                <div class="col">
                                    <button
                                        type="submit"
                                        class="btn btn-primary">Log in</button
                                    >
                                </div>
                                <div class="col-auto">
                                    <a href="/signup">Sign up</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
{/if}

<div class="image-container">
    <img src="/images/luxuryHerrenschuhe.png" alt="Herrenschuh" width="400" />
    <img src="/images/luxuryDamenschuhe.png" alt="Damenschuhe" width="400" />
  </div> 

  