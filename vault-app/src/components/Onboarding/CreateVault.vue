<template>
    <div class="parent-container">
        <div class="create-box">
            <form>
                <h3>Create your Vault</h3>
                <div class="profile-pic-container">
                    <img :src="profilePicture" alt="Profile Picture" class="profile-picture" />
                    <label for="profilePictureInput" class="profile-pic-label">
                        <i class="icon-camera"></i> Change
                    </label>
                    <input type="file" id="profilePictureInput" @change="onFileChange" style="display: none" />
                </div>
                <div class="user-box">
                    <input type="text" v-model="vaultName">
                    <label>Vault Name</label>
                </div>
                <div class="user-box">
                    <input type="text" v-model="description">
                    <label>Description</label>
                </div>
                <div class="nav-buttons">
                    <button @click.prevent="removeVaultForm">Back</button>
                    <button @click.prevent="saveAndProceed">Next</button>
                </div>
            </form>
        </div>
    </div>
</template>

<script>
import { ref } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
    setup() {
        const store = useStore();
        const router = useRouter();

        const profilePicture = ref('https://localhost:8080/pfp.png'); // Set default profile picture
        const vaultName = ref('');
        const description = ref('');

        const onFileChange = (e) => {
            const file = e.target.files[0];
            profilePicture.value = URL.createObjectURL(file);
        }

        const removeVaultForm = () => {
            router.back(); // Navigate to the previous page
        }

        const saveAndProceed = () => {
            if (!vaultName.value.trim() || !description.value.trim()) {
                alert("Please enter valid input");
                return;
            }
            console.log("Committing SAVE_USER_DATA mutation");
            console.log("Vault Name:", vaultName.value);
            console.log("Description:", description.value);
            console.log("Profile Picture:", profilePicture.value);
            store.commit("SAVE_USER_DATA", {
                profilePicture: profilePicture.value,
                vaultName: vaultName.value,
                description: description.value,
            });



            router.push('/configuration'); // Navigate to the VaultConfiguration component
        }

        return {
            profilePicture,
            vaultName,
            description,
            onFileChange,
            removeVaultForm,
            saveAndProceed,
        };
    },
}
</script>

<style scoped>
.create-box h2 {
    margin: 0 0 30px;
    padding: 0;
    color: #fff;
    text-align: center;
}

.create-box .user-box {
    position: relative;
}

.create-box .user-box input {
    width: 100%;
    padding: 10px 0;
    font-size: 16px;
    color: #fff;
    margin-bottom: 30px;
    border: none;
    border-bottom: 1px solid #fff;
    outline: none;
    background: transparent;
}

.create-box .user-box label {
    position: absolute;
    top: 0;
    left: 0;
    padding: 10px 0;
    font-size: 16px;
    color: #fff;
    pointer-events: none;
    transition: .5s;
}

.create-box .user-box input:focus~label,
.create-box .user-box input:valid~label {
    top: -20px;
    left: 0;
    color: #03e9f4;
    font-size: 12px;
}

.create-box form a {
    position: relative;
    display: inline-block;
    padding: 10px 20px;
    color: #03e9f4;
    font-size: 16px;
    text-decoration: none;
    text-transform: uppercase;
    overflow: hidden;
    transition: .5s;
    margin-top: 40px;
    letter-spacing: 4px
}

.create-box a:hover {
    background: #03e9f4;
    color: #fff;
    border-radius: 5px;
    box-shadow: 0 0 5px #03e9f4,
        0 0 25px #03e9f4,
        0 0 50px #03e9f4,
        0 0 100px #03e9f4;
}

.create-box a span {
    position: absolute;
    display: block;
}

.create-box a span:nth-child(1) {
    top: 0;
    left: -100%;
    width: 100%;
    height: 2px;
    background: linear-gradient(90deg, transparent, #03e9f4);
    animation: btn-anim1 1s linear infinite;
}

@keyframes btn-anim1 {
    0% {
        left: -100%;
    }

    50%,
    100% {
        left: 100%;
    }
}

.create-box a span:nth-child(2) {
    top: -100%;
    right: 0;
    width: 2px;
    height: 100%;
    background: linear-gradient(180deg, transparent, #03e9f4);
    animation: btn-anim2 1s linear infinite;
    animation-delay: .25s
}

@keyframes btn-anim2 {
    0% {
        top: -100%;
    }

    50%,
    100% {
        top: 100%;
    }
}

.create-box a span:nth-child(3) {
    bottom: 0;
    right: -100%;
    width: 100%;
    height: 2px;
    background: linear-gradient(270deg, transparent, #03e9f4);
    animation: btn-anim3 1s linear infinite;
    animation-delay: .5s
}

@keyframes btn-anim3 {
    0% {
        right: -100%;
    }

    50%,
    100% {
        right: 100%;
    }
}

.create-box a span:nth-child(4) {
    bottom: -100%;
    left: 0;
    width: 2px;
    height: 100%;
    background: linear-gradient(360deg, transparent, #03e9f4);
    animation: btn-anim4 1s linear infinite;
    animation-delay: .75s
}

@keyframes btn-anim4 {
    0% {
        bottom: -100%;
    }

    50%,
    100% {
        bottom: 100%;
    }
}


h3 {
    text-align: left;
    padding-bottom: 10px;
    color: whitesmoke;
}



.profile-pic-container {
    position: relative;
    width: 100px;
    height: 100px;
    margin-bottom: 50px;
}

.profile-picture {
    width: 100%;
    height: 100%;
    border-radius: 50%;
    object-fit: cover;
}

.profile-pic-label {
    position: absolute;
    bottom: 0;
    right: 0;
    background: rgba(0, 0, 0, 0.6);
    color: #03e9f4;
    padding: 2px 6px;
    border-radius: 4px;
    cursor: pointer;
    transition: background 0.3s;
}

.profile-pic-label:hover {
    background: rgba(0, 0, 0, 0.8);
}

.icon-camera {
    /* Replace this with an appropriate icon. This is a placeholder */
    content: '📷';
}

.user-box {
    position: relative;
}

.user-box input {
    width: 100%;
    padding: 10px 0;
    font-size: 16px;
    color: #fff;
    margin-bottom: 30px;
    border: none;
    border-bottom: 1px solid #fff;
    outline: none;
    background: transparent;
}

.user-box label {
    position: absolute;
    top: 0;
    left: 0;
    padding: 10px 0;
    font-size: 16px;
    color: #fff;
    pointer-events: none;
    transition: .5s;
}

.user-box input:focus~label,
.user-box input:valid~label {
    top: -20px;
    left: 0;
    color: #03e9f4;
    font-size: 12px;
}
</style>