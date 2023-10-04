<template>
    <div class="parent-widget">
        <section>
            <summary>
                <div>
                    <img class="icon" src="../../assets/svg/swap-tx.svg" />
                    <h3>
                        <strong>Swap (HBAR for ETH)</strong>
                    </h3>
                    <span>-39.99 USD</span>
                </div>
            </summary>

            <summary>
                <div>
                    <img class="icon" src="../../assets/svg/deposit-tx.svg" />
                    <h3>
                        <strong>Deposit from Binance</strong>
                    </h3>
                    <span class="plus">+100.00 HBAR</span>
                </div>
            </summary>

            <summary>
                <div>
                    <img class="icon" src="../../assets/svg/send-tx.svg" />
                    <h3>
                        <strong>HBAR sent to 0.0.1549672</strong>
                    </h3>
                    <span>-10 HBAR</span>
                </div>
            </summary>


        </section>
    </div>
</template>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800;900&display=swap");


.icon {
    height: 50px;
    width: 50px;
}

*,
*:after,
*:before {
    box-sizing: border-box;
}

body {
    font-family: "Inter", sans-serif;
    line-height: 1.5;
    min-height: 100vh;
    padding: 5rem 0;
    font-size: clamp(1rem, 2.5vw, 1.125rem);
}

section {
    width: 60%;
    max-width: 800px;
    margin-left: auto;
    margin-right: auto;
    background-color: #121212 ;
    &+section {
        margin-top: 2.5em;
    }
}

h1 {
    font-weight: 700;
    line-height: 1.125;
    font-size: clamp(1.5rem, 2.5vw, 2.5rem);
}

h2 {
    margin-top: .25em;
    color: whitesmoke;
    font-size: clamp(1.125rem, 2.5vw, 1.25rem);

    &+* {
        margin-top: 1.5em;
    }
}

summary div {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

summary &:hover {
    background-color: #f2f5f9;
}

summary h3 {
    display: flex;
    flex-direction: column;
}

summary small {
    color: whitesmoke;
    font-size: 15px;
}

span {
    color: whitesmoke;
}

summary span:first-child {
    width: 4rem;
    height: 4rem;
    border-radius: 10px;
    background-color: #f3e1e1;
    display: flex;
    flex-shrink: 0;
    align-items: center;
    justify-content: center;
    margin-right: 1.25em;

    svg {
        width: 2.25rem;
        height: 2.25rem;
    }
}

summary span :last-child {
    font-weight: 700;
    margin-left: auto;
}

summary .plus {
    color: #289672;
}

summary {
    border-top: solid 1px whitesmoke;
}


details {
    border-bottom: 1px solid;

    &[open] {
        box-shadow: -3px 0 0 #b5bfd9;
    }

    &:first-of-type {
        border-top: 1px solid #b5bfd9;
    }

    &>div {
        padding: 2em 2em 0;
        font-size: .875em;
    }
}

dl div {
    display: flex;
    justify-content: space-between;
    margin-bottom: 1em;
}

dl dt {

    font-weight: 700;
}

dl {
    display: flex;
    flex-wrap: wrap;
}

strong {
    color: whitesmoke;
    font-size: 12px;
}
</style>