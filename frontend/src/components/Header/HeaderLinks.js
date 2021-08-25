/*eslint-disable*/
import React from "react";
import DeleteIcon from "@material-ui/icons/Delete";
import IconButton from "@material-ui/core/IconButton";
// react components for routing our app without refresh
import { Link, withRouter } from "react-router-dom";

// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import Tooltip from "@material-ui/core/Tooltip";

// @material-ui/icons
import { Apps, CloudDownload } from "@material-ui/icons";

// core components
import CustomDropdown from "components/CustomDropdown/CustomDropdown.js";

import styles from "assets/jss/material-kit-react/components/headerLinksStyle.js";

const useStyles = makeStyles(styles);

function HeaderLinks(props) {
  const onLogout = () => {
    props.onLogout(false);
  };
  const classes = useStyles();
  const accountComponent = props.loginStatus == true ?
    <Link to="#" onClick={onLogout} className={classes.dropdownLink}>
      로그아웃하기
  </Link>
    :
    <Link to="/login-page" className={classes.dropdownLink}>
      로그인하기
  </Link>
  return (
    <List className={classes.list}>
      <ListItem className={classes.listItem}>
        <CustomDropdown
          noLiPadding
          buttonText="AI 만들기"
          buttonProps={{
            className: classes.navLink,
            color: "info"
          }}
          buttonIcon={Apps}
          dropdownList={[
            accountComponent,
            <Link to="admin/data-uploading" className={classes.dropdownLink}>
              데이터 업로드
            </Link>,
            <Link to="admin/data-checking" className={classes.dropdownLink}>
              데이터 확인하기
            </Link>,
            <Link to="admin/ai-making" className={classes.dropdownLink}>
              AI 만들기
            </Link>,
            <Link to="admin/ai-checking" className={classes.dropdownLink}>
              AI 결과 확인하기
            </Link>,
          ]}
        />
      </ListItem>
    </List>
  );
}

export default withRouter(HeaderLinks)
