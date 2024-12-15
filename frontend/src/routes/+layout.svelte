<script>
  import "./styles.css";
  import { isAuthenticated, user } from "../store";
  import auth from "../auth.service";
</script>

<nav class="navbar navbar-expand-lg bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="/">Shoerental</a>
    <button
      class="navbar-toggler"
      type="button"
      data-bs-toggle="collapse"
      data-bs-target="#navbarNav"
      aria-controls="navbarNav"
      aria-expanded="false"
      aria-label="Toggle navigation"
    >
      <span class="navbar-toggler-icon" />
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">

          <li class="nav-item">
            <a
              class="nav-link"
              aria-current="page"
              href="/schuheOpen">Schuhe</a
            >
          </li>

          {#if $isAuthenticated && $user.user_roles && $user.user_roles.includes("admin") }
          <li class="nav-item">
          <a class="nav-link" aria-current="page" href="/vermieter"
          >Vermieter</a>
          </li>
          {/if} 
          {#if $isAuthenticated && $user.user_roles && ($user.user_roles.includes("admin") || $user.user_roles.includes("vermieter")) }
        <li class="nav-item">
          <a class="nav-link" href="/schuhe">Schuhe</a>
        </li>
      {/if}

      {#if $isAuthenticated && (!$user.user_roles || (!$user.user_roles.includes("admin") && !$user.user_roles.includes("vermieter")))}
      <li class="nav-item">
        <a class="nav-link" href="/frauenschuh">Frauenschuhe</a>
      </li>
      {/if}

      {#if $isAuthenticated && (!$user.user_roles || (!$user.user_roles.includes("admin") && !$user.user_roles.includes("vermieter")))}
      <li class="nav-item">
        <a class="nav-link" href="/maennerschuh">Männerschuhe</a>
      </li>
      {/if}

      {#if $isAuthenticated && $user.user_roles && $user.user_roles.includes("admin") }
        <li class="nav-item">
          <a class="nav-link" href="/mieter">Mieter</a>
        </li>
      {/if}

      {#if $isAuthenticated &&  $user.user_roles&&  $user.user_roles.includes("admin")}
      <li class="nav-item">
        <a class="nav-link" href="/vermieteteSchuhe">Vermietete Schuhe</a>
      </li>
      {/if}

      {#if $isAuthenticated}
          <li class="nav-item">
            <a class="nav-link" href="/account">Account</a>
          </li>
        {/if}
        {#if $isAuthenticated && (!$user.user_roles || (!$user.user_roles.includes("admin")))}
        <li class="nav-item">
          <a class="nav-link" href="/verifizierung">Verifizierung</a>
        </li>
        {/if}
        <li>
          <a class="nav-link" href="/about">About</a>
        </li>
    </ul>
    <div class="d-flex">
      {#if $isAuthenticated}
        <span class="navbar-text me-2">{$user.name}</span>
        <button type="button" class="btn btn-primary" on:click={auth.logout}
        >Log Out</button>
        {/if}
        </div>
    </div>
  </div>
</nav>

<div class="container mt-3">
  <slot />
</div>
