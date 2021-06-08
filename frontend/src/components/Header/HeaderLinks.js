/*eslint-disable*/
import React from "react";
import DeleteIcon from "@material-ui/icons/Delete";
import IconButton from "@material-ui/core/IconButton";
// react components for routing our app without refresh
import { Link } from "react-router-dom";

// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import Tooltip from "@material-ui/core/Tooltip";

// @material-ui/icons
import { Apps, CloudDownload } from "@material-ui/icons";

// core components
import CustomDropdown from "components/CustomDropdown/CustomDropdown.js";
import Button from "components/CustomButtons/Button.js";

import styles from "assets/jss/material-kit-react/components/headerLinksStyle.js";

const useStyles = makeStyles(styles);

export default function HeaderLinks(props) {
  const classes = useStyles();
  return (
    <List className={classes.list}>
      <ListItem className={classes.listItem}>
        <CustomDropdown
          noLiPadding
          buttonText="AI 만들기"
          buttonProps={{
            className: classes.navLink,
            color : "info"
          }}
          buttonIcon={Apps}
          dropdownList={[
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
      <ListItem className={classes.listItem}>
        <Tooltip
          id="Github"
          title="Follow us on Github"
          placement={window.innerWidth > 959 ? "top" : "left"}
          classes={{ tooltip: classes.tooltip }}
        >
          <Button
            href="https://github.com/Auto-ML-Vision-AI-Web-App/POC"
            target="_blank"
            color="transparent"
            className={classes.navLink}
          >
            <i className={classes.socialIcons + " fab fa-github"} />
          </Button>
        </Tooltip>
      </ListItem>
      <ListItem className={classes.listItem}>
        <Tooltip
          id="instagram-twitter"
          title="Follow us on Github"
          placement={window.innerWidth > 959 ? "top" : "left"}
          classes={{ tooltip: classes.tooltip }}
        >
          <Button
            href="https://www.notion.so/Easy-AI-b10fbb41f7d2472b8727d159ba40ee0c"
            target="_blank"
            color="transparent"
            className={classes.navLink}
          >
            <i className={classes.socialIcons + " fab fa-blogger"} />
          </Button>
        </Tooltip>
      </ListItem>
    </List>
  );
}
