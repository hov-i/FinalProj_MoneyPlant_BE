import os
import time
from selenium import webdriver
import chromedriver_autoinstaller
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from bs4 import BeautifulSoup
import pymysql


def scrape_and_store_cards(url, category, num_iterations):
    chromedriver_autoinstaller.install()
    driver = webdriver.Chrome()
    driver.get(url)

    try:
        for _ in range(num_iterations):
            more_button = WebDriverWait(driver, 10).until(EC.element_to_be_clickable((By.CSS_SELECTOR, '.more')))
            more_button.click()
            time.sleep(0.4)  # 0.4 seconds delay
        page_source = driver.page_source

        card_imgs = []
        card_links = []

        soup = BeautifulSoup(page_source, 'html.parser')
        names = soup.select("b.name")
        descs = soup.select("p.desc")
        annual_fees = soup.select("i.annual_fee")

        links = soup.select("a.anchor")
        for link_tag in links:
            card_link = link_tag.get("href")
            card_links.append(card_link)

        imgs = soup.select("img.img")
        for img_tag in imgs:
            card_img = img_tag.get("src")
            card_imgs.append(card_img)

        card_names = [element.text for element in names]
        card_descs = [element.text for element in descs]
        card_annual_fees = [element.text for element in annual_fees]

        conn = pymysql.connect(host="127.0.0.1", user="MoneyPlant", password="1234", db="money_plant", charset="utf8")
        cursor = conn.cursor()

        for name, desc, img, link, annual_fee in zip(card_names, card_descs, card_imgs, card_links, card_annual_fees):
            query = "INSERT INTO card_list (card_name, card_category, card_desc, card_img, card_link, card_annual_fee) VALUES (%s, %s, %s, %s, %s, %s)"
            values = (name, category, desc, img, 'https://card-search.naver.com'+link, annual_fee)
            cursor.execute(query, values)

        conn.commit()
        conn.close()

    except TimeoutError:
        print("Timeout occurred while scraping and storing cards.")


# Example usage
categories = {
    "반려동물": ("https://card-search.naver.com/category-list?category=pet", 5),
    "주유": ("https://card-search.naver.com/category-list?category=gas", 10),
    "식비": ("https://card-search.naver.com/category-list?category=food", 7),
    "문화/레저": ("https://card-search.naver.com/category-list?category=culture", 9),
    "마트/편의점": ("https://card-search.naver.com/category-list?category=mart", 8),
    "패션/미용": ("https://card-search.naver.com/category-list?category=fashion", 6)
}

for category, (url, num_iterations) in categories.items():
    scrape_and_store_cards(url, category, num_iterations)