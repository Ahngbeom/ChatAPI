import {signUpFormAuthorities, signUpFormAuthorityInputList, signUpInputGuides, signUpJson} from "./signup.js";

/** Authority */
export const setMovementForAuthority = function () {
    setPopoverToSignUpForm('hover focus', signUpFormAuthorities, signUpInputGuides.authorities);
    signUpFormAuthorityInputList.forEach(auth => {
        auth.addEventListener('change', function (e) {
            if (e.target.checked) {
                signUpJson.authorities.add(e.target.value);
                if (signUpFormAuthorities.classList.contains("is-invalid"))
                    signUpFormAuthorities.classList.replace('is-invalid', 'is-valid');
                else
                    signUpFormAuthorities.classList.add('is-valid');
            }
            else
                signUpJson.authorities.delete(e.target.value);

            if (signUpJson.authorities.size === 0) {
                if (signUpFormAuthorities.classList.contains("is-valid"))
                    signUpFormAuthorities.classList.replace('is-valid', 'is-invalid');
                else
                    signUpFormAuthorities.classList.add('is-invalid');
            }
        });
    });
}