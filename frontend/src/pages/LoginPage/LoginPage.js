import React from "react";
import axios from 'axios';
import {setCookie, getCookie, removeCookie} from 'components/Cookie.js';

// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import InputAdornment from "@material-ui/core/InputAdornment";
// @material-ui/icons
import Icon from "@material-ui/core/Icon";
import PermIdentityIcon from '@material-ui/icons/PermIdentity';
// core components
import Header from "components/Header/Header.js";
import HeaderLinks from "components/Header/HeaderLinks.js";
import Alert from '@material-ui/lab/Alert';
import Footer from "components/Footer/Footer.js";
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import Button from "components/CustomButtons/Button.js";
import Card from "components/Card/Card.js";
import CardBody from "components/Card/CardBody.js";
import CardHeader from "components/Card/CardHeader.js";
import CardFooter from "components/Card/CardFooter.js";
import CustomInput from "components/CustomInput/CustomInput.js";

import styles from "assets/jss/material-kit-react/views/loginPage.js";

import image from "assets/img/farm-bg.jpeg";

const useStyles = makeStyles(styles);

export default function LoginPage(props) {
  const [cardAnimaton, setCardAnimation] = React.useState("cardHidden");
  const [userId, setUserId] = React.useState("");
  const [userPw, setUserPw] = React.useState("");

  setTimeout(function () {
    setCardAnimation("");
  }, 700);
  const classes = useStyles();
  const { ...rest } = props;

  //Login function
  const onLogin = () => {
    console.log(userId + "..." + userPw)
    const api = axios.create({
      baseURL: 'http://localhost:8080'
    })
    api.post('/signin', null, {
      params: {
        userId: userId,
        password: userPw
      }
    }).then(function (response) {
      console.log(response.data);
      localStorage.setItem('refresh-token',response.data['refresh-token']);
      setCookie('access-token',response.data['access-token']);
      setCookie('user-name',userId);

      document.getElementById("login_error_alert").style.display="none";
      props.history.push({
        pathname: '/'
      })

    }).catch(function (error) {
      document.getElementById("login_error_alert").style.display="block";
      console.log(error);
    });
  };

  return (
    <div>
      <Header
        absolute
        color="transparent"
        brand="Eavy AI"
        rightLinks={<HeaderLinks />}
        {...rest}
      />
      <div
        className={classes.pageHeader}
        style={{
          backgroundImage: "url(" + image + ")",
          backgroundSize: "cover",
          backgroundPosition: "top center",
        }}
      >
        <div className={classes.container}>
          <GridContainer justify="center">
            <GridItem xs={12} sm={12} md={4}>
              <Card className={classes[cardAnimaton]}>
                <form className={classes.form}>
                  <CardHeader color="info" className={classes.cardHeader}>
                    <h4>Login</h4>

                  </CardHeader>
                  <Alert id="login_error_alert" style={{display:'none'}} severity="error">로그인에 실패했습니다.<br></br>아이디와 패스워드를 확인해주세요.</Alert>
                  <p className={classes.divider}>Go to Make Your Own AI</p>
                  <CardBody>
                    <CustomInput
                      labelText="Id..."
                      id="id"
                      formControlProps={{
                        fullWidth: true,
                      }}
                      inputProps={{
                        onChange: (event) => setUserId(event.target.value),
                        type: "id",
                        endAdornment: (
                          <InputAdornment position="end">
                            <PermIdentityIcon className={classes.inputIconsColor} />
                          </InputAdornment>
                        ),
                      }}
                    />
                    <CustomInput
                      labelText="Password"
                      id="pass"
                      formControlProps={{
                        fullWidth: true,
                      }}
                      inputProps={{
                        onChange: (event) => setUserPw(event.target.value),
                        type: "password",
                        endAdornment: (
                          <InputAdornment position="end">
                            <Icon className={classes.inputIconsColor}>
                              lock_outline
                            </Icon>
                          </InputAdornment>
                        ),
                        autoComplete: "off",
                      }}
                    />
                  </CardBody>
                  <CardFooter className={classes.cardFooter}>
                    <Button onClick={onLogin} simple color="info" size="lg">
                      Login
                    </Button>
                  </CardFooter>
                </form>
              </Card>
            </GridItem>
          </GridContainer>
        </div>
        <Footer whiteFont />
      </div>
    </div>
  );
}
