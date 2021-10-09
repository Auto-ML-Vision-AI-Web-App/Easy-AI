import React from "react";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";

// @material-ui/icons
import Face from "@material-ui/icons/Face";
import GitHubIcon from '@material-ui/icons/GitHub';
import BookIcon from '@material-ui/icons/Book';
import Link from '@material-ui/core/Link';
// core components
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import CustomTabs from "components/CustomTabs/CustomTabs.js";

import styles from "assets/jss/material-kit-react/views/componentsSections/tabsStyle.js";

import stylesFont from "assets/jss/material-kit-react/views/componentsSections/typographyStyle.js";

const useStyles = makeStyles(styles);
const useFontStyles = makeStyles(stylesFont);

export default function SectionTabs() {
  const classes = useStyles();
  const classesFont = useFontStyles();
  return (
    <div className={classes.section}>
      <div className={classes.container}>
        <div id="nav-tabs">
            <h1 className={classesFont.title}>What is Easy-AI ?</h1>
          <GridContainer>
            <GridItem xs={12} sm={12} md={12}>
              <CustomTabs
                headerColor="info"
                tabs={[
                  {
                    tabName: "Easy AI",
                    tabIcon: Face,
                    tabContent: (
                      <p>
                        {/* className={classes.textCenter}*/}
                        <h5>우리 Easy-AI는요...</h5>
                      </p>
                    ),
                  },
                  {
                    tabName: "Github",
                    tabIcon: GitHubIcon,
                    tabContent: (
                      <p>
                        <h3>Easy AI는 아래 Github를 통해 만날 수 있습니다.</h3>
                        <h5>LINK : https://github.com/Auto-ML-Vision-AI-Web-App</h5>
                      </p>
                    ),
                  },
                  {
                    tabName: "Blog",
                    tabIcon: BookIcon,
                    tabContent: (
                      <p>
                        <h3>Easy AI는 어떤 기술로 만들어졌는지, 아래 블로그를 통해 확인하세요.</h3>
                        <h5>LINK : 블로그 링크 줘</h5>
                      </p>
                    ),
                  },
                ]}
              />
            </GridItem>
          </GridContainer>
        </div>
      </div>
    </div>
  );
}
